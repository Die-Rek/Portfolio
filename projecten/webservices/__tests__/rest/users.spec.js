
const expect = require('chai').expect;
const { beforeEach,before, afterEach, describe, it } = require('mocha');

const { tables } = require('../../src/data');
const { withServer } = require('../helpers');

const data = {
  users: [{
    userId: 1,
    name: 'Senne Dierick',
    birthDate: '2002-01-21',
    email: 'senne.dierick@student.hogent.be',
  },
  {
    userId: 2,
    name: 'Myrthe Onghena',
    birthDate: '2002-10-22',
    email: 'myrthe.onghena@student.kdg.be',
  },
  {
    userId: 3,
    name: 'Elise De Bock',
    birthDate: '1995-08-07',
    email: 'elise.debock@student.odisee.be',
  }],
};

const dataToDelete = {
  users: [1,2,3],
};

describe('users', () => {
  let request;
  let knex;
  let authHeader;

  withServer(({ request : r, knex: k, authHeader: h}) => {
    request = r;
    knex = k;
    authHeader = h;
  });

  before(async () => {
    await knex.raw(`alter table ${tables.order} AUTO_INCREMENT=1`);
    await knex.raw(`alter table ${tables.user} AUTO_INCREMENT=1`);
    await knex.raw(`alter table ${tables.AI} AUTO_INCREMENT=1`);
  });

  beforeEach(async () => {
    await knex(tables.user).insert(data.users);
  });

  afterEach(async () => {
    await knex(tables.user).whereIn('userId', dataToDelete.users).delete();
    await knex.raw(`alter table ${tables.user} AUTO_INCREMENT=1`);

  });
    
  const url = '/api/users/';
  describe('GET /api/users/' , () => {

    it('should return 200 and all users', async () => {
      const response = await request.get(url).set('Authorization', authHeader);
      expect(response.status).to.eq(200);
      expect(response.body.users.length).to.eq(3);

    });
  });

  describe('GET /api/users/:userId', () => {

    it('should return 200 and the user with the given userId', async () => {
      const response = await request.get(url + '1').set('Authorization', authHeader);
      expect(response.status).to.eq(200);
      expect(response.body.user.id).to.eq(1);
      expect(response.body.user.name).to.eq('Senne Dierick');
      expect(response.body.user.birthDate).to.include('2002-01-2');
      expect(response.body.user.email).to.eq('senne.dierick@student.hogent.be');
    });

    it('should return 404 when the user does not exist', async () => {
      const response = await request.get(url + '4').set('Authorization', authHeader);
      expect(response.status).to.eq(404);
    });
  });

  describe('POST /api/users/', () => {
      
    it('should return 201 and the created user', async () => {
      const response = await request.post(url).set('Authorization', authHeader).send({
        name: 'Jefke',
        birthDate: '2002-01-21',
        email: 'jefke@student.hogent.be',
      });
      expect(response.status).to.eq(201);
      expect(response.body.user.id).to.eq(4);
      expect(response.body.user.name).to.eq('Jefke');
      expect(response.body.user.birthDate).to.include('2002-01-2');
      expect(response.body.user.email).to.eq('jefke@student.hogent.be');
    });
  });

  describe('PUT /api/users/:userId', () => {
        
    it('should return 200 and the updated user', async () => {
      const response = await request.put(url + '1').set('Authorization', authHeader).send({
        name: 'Jefke',
        birthDate: '2002-01-21',
        email: 'jefke@student.hogent.be',
      });
      expect(response.status).to.eq(200);
      expect(response.body.user.id).to.eq(1);
      expect(response.body.user.name).to.eq('Jefke');
      expect(response.body.user.birthDate).to.include('2002-01-2');
      expect(response.body.user.email).to.eq('jefke@student.hogent.be');
    });

    it('should return 404 when the user does not exist', async () => {
      const response = await request.put(url + '4').set('Authorization', authHeader).send({
        name: 'Jefke',
        birthDate: '2002-01-21',
        email: 'jefke@student.hogent.be',
      });
      expect(response.status).to.eq(404);
    });
  });
  
  describe('DELETE /api/users/:userId', () => {
          
    it('should return 204 and the deleted user', async () => {
      const response = await request.delete(url + '1').set('Authorization', authHeader);
      expect(response.status).to.eq(204);
    });
  });
});

