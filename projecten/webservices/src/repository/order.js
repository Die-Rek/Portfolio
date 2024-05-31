//WIP

const { tables, getKnex } = require('../data/index');
const { getLogger } = require('../core/logging');


const SELECT_COLUMNS = [
  `${tables.order}.orderId`, `${tables.AI}.AIid`, `${tables.AI}.learning_method`,
  `${tables.order}.userId`, `${tables.user}.name`, `${tables.order}.orderDate`,
  `${tables.order}.deliveryDate`, `${tables.order}.paymentMethod`, 
];

const getAll = async () => {
  const orders = await getKnex()(tables.order)
    .select(SELECT_COLUMNS)
    .join(tables.orderDetail, `${tables.order}.orderId`,'=', `${tables.orderDetail}.orderId`)
    .join(tables.AI, `${tables.orderDetail}.AIid`,'=', `${tables.AI}.AIid`)
    .join(tables.user, `${tables.order}.userId`,'=', `${tables.user}.userId`)
    .orderBy('orderId', 'asc');
  return orders.map(formatOrder);
};

const formatOrder = ({orderId, orderDate, deliveryDate, paymentMethod, userId, name, AIid, learning_method, ...rest}) => ({
  ...rest,
  order: {
    id: orderId,
    orderDate: orderDate,
    deliveryDate: deliveryDate,
    paymentMethod: paymentMethod,
  },
  user: {
    id: userId,
    name: name,
  },
  AI:{
    id: AIid,
    learning_method: learning_method,
  },
});

const getById = async (orderId) => {
  const order = await getKnex()(tables.order)
    .first(SELECT_COLUMNS)
    .join(tables.user, `${tables.order}.userId`,'=', `${tables.user}.userId`)
    .join(tables.orderDetail, `${tables.order}.orderId`,'=', `${tables.orderDetail}.orderId`)
    .join(tables.AI, `${tables.orderDetail}.AIid`,'=', `${tables.AI}.AIid`)
    .where(`${tables.order}.orderId`, orderId);

  return order && formatOrder(order);
};

const create = async ({userId, AIid, orderDate, deliveryDate, paymentMethod}) => {
  try{
    const [id] = await getKnex()(tables.order).insert({
      userId,
      orderDate,
      deliveryDate,
      paymentMethod,
    });
    await getKnex()(tables.orderDetail).insert({
      orderId: id,
      AIid,
      prodAmount: 1,
    });
    return await getById(id);
  } catch (error) {
    const logger = getLogger();
    logger.error('Error in create order', { error });
    throw error;
  }
};

const updateById = async ({orderId, userId, AIid, orderDate, deliveryDate, paymentMethod}) => {
  try{
    const id = await getKnex()(tables.order).update({
      userId,
      orderDate,
      deliveryDate,
      paymentMethod,
    })
      .where('orderId', orderId);
    await getKnex()(tables.orderDetail).update({
      AIid,
      prodAmount: 1,
    })
      .where('orderId', orderId);
    return await getById(id);
  } catch (error) {
    const logger = getLogger();
    logger.error('Error in update order', { error });
    throw new Error('Error in update order');
  }
};

const deleteById = async (orderId) => {
  const rowsAffected = await getKnex()(tables.order)
    .delete()
    .where('orderId', orderId);
  return rowsAffected > 0;
};

const getCount = async () => {
  const count = await getKnex() (tables.order)
    .count()
    .first();
  return count['count(*)'];
};

module.exports = {
  getAll,
  getById,
  create,
  updateById,
  deleteById,
  getCount,
};