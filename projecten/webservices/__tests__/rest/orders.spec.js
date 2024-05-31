
const expect = require('chai').expect;
const { beforeEach, afterEach, before, after, describe, it } = require('mocha');

const { tables } = require('../../src/data');
const { withServer } = require('../helpers');

const data={
  orders :[{
    orderId: 1,
    userId: 1,
    orderDate: '2021-10-21',
    deliveryDate: '2021-11-02',
    paymentMethod: 'overschrijving',
  },
  {
    orderId: 2,
    userId: 3,
    orderDate: '2021-11-07',
    deliveryDate: '2021-12-02',
    paymentMethod: 'overschrijving',
  },
  {
    orderId: 3,
    userId: 2,
    orderDate: '2021-12-08',
    deliveryDate: '2021-12-15',
    paymentMethod: 'bancontact',
  }],
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
  AI: [{
    AIid: 1,
    learning_method: 'supervised learning',
    training_time: 200,
    activationKey:'WBjo80rXZa',
    unitPrice: 1000,
  },
  {
    AIid: 2,
    learning_method: 'unsupervised learning',
    training_time: 300,
    activationKey:'INGWMcN9gb',
    unitPrice: 1500,
  },
  {
    AIid: 3,
    learning_method: 'unsupervised learning',
    training_time: 50,
    activationKey:'us7y1sEMBY',
    unitPrice: 500,
  },
  {
    AIid: 4,
    learning_method: 'reinforced learning',
    training_time: 500,
    activationKey:'RQ2e7ydPbH',
    unitPrice: 2000,
  }],
  orderDetails: [{
    orderId: 1,
    AIid: 1,
    prodAmount: 1,
  },
  {
    orderId: 2,
    AIid: 2,
    prodAmount: 1,
  },
  {
    orderId: 2,
    AIid: 3,
    prodAmount: 1,
  },
  {
    orderId: 3,
    AIid: 4,
    prodAmount: 1,
  }],
};

const dataToDelete = {
  orders: [1,2,3],
  users: [1,2,3],
  AI: [1,2,3,4],
};

describe('orders', () => {

  let request;
  let knex;
  let authHeader;
    
  withServer(({ request: r, knex: k, authHeader: h }) => {
    request = r;
    knex = k;
    authHeader = h;
  });

  beforeEach(async () => {
    await knex(tables.user).insert(data.users);
    await knex(tables.order).insert(data.orders);
    await knex(tables.AI).insert(data.AI);
    await knex(tables.orderDetail).insert(data.orderDetails);
  });

  afterEach(async () => {
    await knex(tables.order).whereIn('orderId', dataToDelete.orders).delete();
    await knex(tables.user).whereIn('userId', dataToDelete.users).delete();
    await knex(tables.AI).whereIn('AIid', dataToDelete.AI).delete();
    await knex(tables.orderDetail).whereIn('orderId', dataToDelete.orders).delete();
    await knex.raw(`alter table ${tables.order} AUTO_INCREMENT=1`);
    await knex.raw(`alter table ${tables.user} AUTO_INCREMENT=1`);
    await knex.raw(`alter table ${tables.AI} AUTO_INCREMENT=1`);
  });

  const url = '/api/orders/';
  describe('GET /api/orders/', () => {

    it('should return 200 and all orders', async () => {
      const response = await request.get(url).set('Authorization', authHeader);
      
      expect(response.status).to.equal(200);
      expect(response.body.orders.length).to.equal(4);
      expect(response.body.orders[0].order.id).to.equal(1);
      expect(response.body.orders[0].order.orderDate).to.include('2021-10-20');
      expect(response.body.orders[0].order.deliveryDate).to.include('2021-11-01');
      expect(response.body.orders[0].order.paymentMethod).to.equal('overschrijving');
      expect(response.body.orders[0].user.id).to.equal(1);
      expect(response.body.orders[0].user.name).to.equal('Senne Dierick');
      expect(response.body.orders[0].AI.id).to.equal(1);
      expect(response.body.orders[0].AI.learning_method).to.equal('supervised learning');
    });
  });

  describe('GET /api/orders/:id', () => {

    it('should return 200 and the order', async () => {
      const response = await request.get(url + '1').set('Authorization', authHeader);
      expect(response.status).to.eq(200);
      expect(response.body.order.id).to.equal(1);
      expect(response.body.order.orderDate).to.include('2021-10-20');
      expect(response.body.order.deliveryDate).to.include('2021-11-01');
      expect(response.body.order.paymentMethod).to.equal('overschrijving');
      expect(response.body.user.id).to.equal(1);
      expect(response.body.user.name).to.equal('Senne Dierick');
      expect(response.body.AI.id).to.equal(1);
      expect(response.body.AI.learning_method).to.equal('supervised learning');

    });

    it('should return 404 when the order does not exist', async () => {
      const response = await request.get(url + '10').set('Authorization', authHeader);
      expect(response.status).to.eq(404);
    });
  });

  describe('POST /api/orders/', () => {

    it('should return 201 and the created order', async () => {
      const response = await request.post(url).send(
        {
          'userId' : 1,
          'AIid' : 2,
          'orderDate': '2021-10-21',
          'deliveryDate': '2021-11-02',
          'paymentMethod': 'overschrijving',
        }).set('Authorization', authHeader);
      expect(response.status).to.eq(201);
      expect(response.body.user.id).to.eq(1);
      expect(response.body.AI.id).to.eq(2);
      expect(response.body.order.orderDate).to.include('2021-10-20');
      expect(response.body.order.deliveryDate).to.include('2021-11-01');
      expect(response.body.order.paymentMethod).to.eq('overschrijving');

    });
  });

  describe('PUT /api/orders/:id', () => {


    it('should return 200 and the updated order', async () => {
      const response = await request.put(url + '1').send(
        {
          'userId': 1,
          'AIid': 2,
          'orderDate': '2021-10-21',
          'deliveryDate': '2021-11-02',
          'paymentMethod': 'payconiq',
        }).set('Authorization', authHeader);
      expect(response.status).to.eq(200);
      expect(response.body.order.id).to.eq(1);
      expect(response.body.AI.id).to.eq(2);
      expect(response.body.user.id).to.eq(1);
      expect(response.body.order.orderDate).to.include('2021-10-2');
      expect(response.body.order.deliveryDate).to.include('2021-11-0');
      expect(response.body.order.paymentMethod).to.eq('payconiq');


    });

    it('should return 404 when the transaction does not exist', async () => {
      const response = await request.put(url + '10').send(
        {
          userId: 1,
          AIid: 1,
          orderDate: '2021-10-21',
          deliveryDate: '2021-11-02',
          paymentMethod: 'overschrijving',
        }).set('Authorization', authHeader);
      expect(response.status).to.eq(404);
    });
  });

  describe('DELETE /api/orders/:id', () => {


    it('should return 204', async () => {
      const response = await request.delete(url + '1').set('Authorization', authHeader);
      expect(response.status).to.eq(204);

    });

    it('should return 404 when the transaction does not exist', async () => {
      const response = await request.delete(url + '4').set('Authorization', authHeader);
      expect(response.status).to.eq(404);
    });
  });

});