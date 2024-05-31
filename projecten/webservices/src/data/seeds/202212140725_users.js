const { tables } = require('../index');

module.exports = {
  seed: async (knex) => {
    await knex(`${tables.user}`).delete();

    await knex(`${tables.user}`).insert([
      { userId: 1, name: 'Senne Dierick', birthDate: '2002-01-21', email: 'senne.dierick@student.hogent.be'},
      { userId: 2, name: 'Myrthe Onghena', birthDate: '2002-10-22', email: 'myrthe.onghena@student.hogent.be'},
      { userId: 3, name: 'Elise De Bock', birthDate: '1995-08-07', email: 'elise.debock@student.odisee.be'},
    ]);
  },
};