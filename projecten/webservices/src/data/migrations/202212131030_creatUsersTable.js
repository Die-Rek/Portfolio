const { tables } = require('../index');
module.exports = {
  up: async (knex) => {
    await knex.schema.createTable(`${tables.user}`, (table) => {
      table.increments('userId').primary();
      table.string('name', 255).notNullable();
      table.date('birthDate').notNullable();
      table.string('email', 255);          
    });
  },
  down: (knex) => {
    knex.dropTableIfExists(`${tables.user}`);
  },
};