const { tables } = require('../index');
module.exports = {
  up: async (knex) =>{
    await knex.schema.createTable(`${tables.order}`, (table) => {
      table.increments('orderId');
      table.integer('userId').unsigned().notNullable();
      table.date('orderDate').notNullable();
      table.date('deliveryDate');
      table.string('paymentMethod').notNullable();
      table.foreign('userId', 'fk_orders_users').references(`${tables.user}.userId`).onDelete('CASCADE');
    });
  },
  down: (knex) => {
    return knex.schema.dropTableIfExists(`${tables.order}`);
  },
};