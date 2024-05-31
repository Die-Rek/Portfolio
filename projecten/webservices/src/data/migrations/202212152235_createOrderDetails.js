const { tables } = require('../index');

module.exports = {
  up: async (knex) => {
    await knex.schema.createTable(`${tables.orderDetail}`, (table) =>{
      table.integer('orderId').unsigned().notNullable();
      table.integer('AIid').unsigned().notNullable();
      table.integer('prodAmount').notNullable();
      table.primary(['orderId', 'AIid']);
      table.foreign('AIid', 'fk_orderDetails_AIs').references(`${tables.AI}.AIid`).onDelete('CASCADE');
      table.foreign('orderId', 'fk_orderDetails_orders').references(`${tables.order}.orderId`).onDelete('CASCADE');

    });
  },
  down: (knex) => {
    return knex.schema.dropTableIfExists(`${tables.orderDetail}`);
  },
};
