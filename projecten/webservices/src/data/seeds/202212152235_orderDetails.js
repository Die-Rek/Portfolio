const { tables } = require('../index');
module.exports = {
  seed: async (knex) => {
    await knex(`${tables.orderDetail}`).delete();

    await knex(`${tables.orderDetail}`).insert([
      { orderId: 1, AIid: 1, prodAmount: 1},
      { orderId: 2, AIid: 2, prodAmount: 1},
      { orderId: 2, AIid: 3, prodAmount: 1},
      { orderId: 3, AIid: 4, prodAmount: 1},
    ]);
  },
}; 