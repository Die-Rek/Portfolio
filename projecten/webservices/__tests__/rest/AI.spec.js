const expect = require('chai').expect;
const { beforeEach, afterEach,  describe, it } = require('mocha');

const { tables } = require('../../src/data');
const { withServer } = require('../helpers');

const data={
  AI :[{
    AIid: 1,
    learning_method: 'supervised learning',
    training_time: 200,
    activationKey: 'WBjo80rXZa',
    unitPrice: 1000,
  },
  {
    AIid: 2,
    learning_method: 'unsupervised learning',
    training_time: 300,
    activationKey: 'INGWMcN9gb',
    unitPrice: 1500,
  },
  {
    AIid: 3,
    learning_method: 'unsupervised learning',
    training_time: 50,
    activationKey: 'us7y1sEMBY',
    unitPrice: 500,
  },
  {
    AIid: 4,
    learning_method: 'reinforced learning',
    training_time: 500,
    activationKey: 'RQ2e7ydPbH',
    unitPrice: 2000,
  }],
};

const dataToDelete = {
  AI: [1,2,3,4],
};

describe('AI', () => {

  let request;
  let authHeader;
  let knex;

  withServer(({ request: r, authHeader: a, knex: k }) => {
    request = r;
    authHeader = a;
    knex = k;
  });

  beforeEach(async () => {
    await knex.raw(`alter table ${tables.AI} AUTO_INCREMENT = 1`);
    await knex(tables.AI).insert(data.AI);
  });

  afterEach(async () => {
    await knex(tables.AI).whereIn('AIid', dataToDelete.AI).del();
  });

  const url = '/api/AIs/';
  describe('GET /api/AIs/', () => {
    it('should return 200 and all AIs', async () => {
      const response = await request.get(url).set(authHeader);
      expect(response.status).to.equal(200);
      expect(response.body.length).to.eq(4);
    });
  });

  describe('GET /api/AIs/:AIid', () => {

    it('should return 200 and the AI with the given id', async () => {
      const response = await request.get(url + '1').set('Authorization', authHeader);
      expect(response.status).to.eq(200);
      expect(response.body.AI.id).to.eq(1);
      expect(response.body.AI.learning_method).to.eq('supervised learning');
      expect(response.body.AI.training_time).to.eq('200');
      expect(response.body.AI.activationKey).to.eq('WBjo80rXZa');
      expect(response.body.AI.unitPrice).to.eq('1000');
    });

    it('should return 404 when the user does not exist', async () => {
      const response = await request.get(url + '4').set('Authorization', authHeader);
      expect(response.status).to.eq(404);
    });
  });

  describe('POST /api/AIs/', () => {
    it('should return 201 and the created AI', async () => {
      const response = await request.post(url).set('Authorization', authHeader).send({
        learning_method: 'supervised learning',
        training_time: 200,
        activationKey: 'WBjo80rXZa',
        unitPrice: 1000,
      });
      expect(response.status).to.eq(201);
      expect(response.body.AI.id).to.eq(5);
      expect(response.body.AI.learning_method).to.eq('supervised learning');
      expect(response.body.AI.training_time).to.eq('200');
      expect(response.body.AI.activationKey).to.eq('WBjo80rXZa');
      expect(response.body.AI.unitPrice).to.eq('1000');
    });
  });

  describe('PUT /api/AIs/:AIid', () => {
    it('should return 200 and the updated AI', async () => {
      const response = await request.put(url + '1').set('Authorization', authHeader).send({
        learning_method: 'reinforced learning',
        training_time: 200,
        activationKey: 'WBjo80rXZa',
        unitPrice: 1000,
      });
      expect(response.status).to.eq(200);
      expect(response.body.AI.id).to.eq(1);
      expect(response.body.AI.learning_method).to.eq('reinforced learning');
      expect(response.body.AI.training_time).to.eq('200');
      expect(response.body.AI.activationKey).to.eq('WBjo80rXZa');
      expect(response.body.AI.unitPrice).to.eq('1000');
    });

    it('should return 404 when the AI does not exist', async () => {
      const response = await request.put(url + '4').set('Authorization', authHeader).send({
        learning_method: 'supervised learning',
        training_time: 200,
        activationKey: 'WBjo80rXZa',
        unitPrice: 1000,
      });
      expect(response.status).to.eq(404);
    });
  });
});



