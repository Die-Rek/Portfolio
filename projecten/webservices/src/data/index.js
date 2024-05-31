const { join } = require('path');

const config = require('config');
const knex = require('knex');

const NODE_ENV = config.get('env');
const isDevelopment = NODE_ENV === 'development';

const DATABASE_CLIENT = config.get('database.client');
const DATABASE_NAME = config.get('database.name');
const DATABASE_HOST = config.get('database.host');
const DATABASE_PORT = config.get('database.port');
const DATABASE_USERNAME = config.get('database.username');
const DATABASE_PASSWORD = config.get('database.password');

const { getLogger } = require('../core/logging');

let knexInstance;

const knexLoger = (logger, level) => (message) =>{
  if (message.sql){
    logger.log(level, message.sql);
  } else {
    logger.log(level, JSON.stringify(message));
  }
};

async function initializeData() {
  const knexOptions = {
    client: DATABASE_CLIENT,
    debug: isDevelopment,
    connection: {
      host: DATABASE_HOST,
      port: DATABASE_PORT,
      user: DATABASE_USERNAME,
      password: DATABASE_PASSWORD,
      insecureAuth: isDevelopment,
    },
    migrations: {
      tableName: 'knex_meta',
      directory: join('src', 'data', 'migrations'),
    },
    seeds: {
      directory: join('src', 'data', 'seeds'),
    },
    log: {
      debug: knexLoger(getLogger(), 'debug'),
      warn: knexLoger(getLogger(), 'warn'),
      error: knexLoger(getLogger(), 'error'),
    },
  };
    
    
  knexInstance = knex(knexOptions);

  const logger = getLogger();

  try {
    await knexInstance.raw('SELECT 1');
    await knexInstance.raw(`CREATE DATABASE IF NOT EXISTS ${DATABASE_NAME}`);
    //connectie verwijderen en opnieuw maken met de juiste database
    await knexInstance.destroy();
    knexOptions.connection.database = DATABASE_NAME;
    knexInstance = knex(knexOptions);
    await knexInstance.raw('SELECT 1');
  } catch (error) {
    logger.error(error.message, { error });
    throw new Error('Database initialization failed');
  }

  let knexMigrationFailed = true;

  try{
    await knexInstance.migrate.latest();
    knexMigrationFailed = false;
  } catch (error) {
    logger.error('error while migrating database', { error });
    throw new Error('Database migration failed');
  }

  if (knexMigrationFailed) {
    try {
      await getKnex().migrate.down();
    } catch (error) {
      logger.error('migration failed', { error });
    }
    throw new Error('Database migration failed');
  }

  if (isDevelopment) {
    try{
      await knexInstance.seed.run();
    } catch (error){
      logger.error('error while seeding database', { error });

    }
  }

  return knexInstance;
}

async function shutDownData() {
  const logger = getLogger();

  logger.info('Shutting down data layer');

  await knexInstance.destroy();
  knexInstance = null;

  logger.info('Data layer shut down');
}

function getKnex() {
  if (!knexInstance) {
    throw new Error('Please initialize data layer first');
  }
  return knexInstance;
}
    

const tables = Object.freeze({
  order: 'orders',
  orderDetail : 'orderDetails',
  AI: 'AI',
  user: 'users',
});

module.exports = {
  initializeData,
  getKnex,
  tables,
  shutDownData,
};