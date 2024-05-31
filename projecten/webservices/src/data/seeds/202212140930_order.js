const { tables } = require('../index');
module.exports = {
  seed: async (knex) => {
    await knex(`${tables.order}`).delete();

    await knex(`${tables.order}`).insert([
      { orderId: 1, userId: 1, orderDate: '2021-10-21', deliveryDate: '2021-11-02', paymentMethod: 'overschrijving' },
      { orderId: 2, userId: 3, orderDate: '2021-11-07', deliveryDate: '2021-12-02', paymentMethod: 'overschrijving' },
      { orderId: 3, userId: 2, orderDate: '2021-12-08', deliveryDate: '2021-12-15', paymentMethod: 'bancontact' },
    ]);
  },
};