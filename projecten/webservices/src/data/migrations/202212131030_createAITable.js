const { tables } = require('../index');

module.exports = {
  up: async (knex) => {
    await knex.schema.createTable(`${tables.AI}`, (table) => {
      table.increments('AIid').primary();
      table.string('learning_method', 255).notNullable();
      table.integer('training_time').notNullable();
      table.string('activationKey').notNullable();
      table.integer('unitPrice').notNullable();
      table.unique('activationKey');
    });
  },
  down: (knex) => {
    return knex.schema.dropTableIfExists(`${tables.AI}`);
  },
};