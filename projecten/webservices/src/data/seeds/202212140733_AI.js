const { tables } = require('../index');
module.exports = {
  seed: async (knex) => {
    await knex(`${tables.AI}`).delete();

    await knex(`${tables.AI}`).insert([
      {AIid: 1, learning_method: 'supervised learning', training_time: 200, activationKey: 'WBjo80rXZa', unitPrice: 1000},
      {AIid: 2, learning_method: 'unsupervised learning', training_time: 300, activationKey: 'INGWMcN9gb', unitPrice: 1500},
      {AIid: 3, learning_method: 'unsupervised learning', training_time: 50, activationKey: 'us7y1sEMBY', unitPrice: 500},
      {AIid: 4, learning_method: 'reinforced learning', training_time: 500, activationKey: 'RQ2e7ydPbH', unitPrice: 2000},
    ]);
  },
};