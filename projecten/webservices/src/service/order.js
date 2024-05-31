const orderRepository = require('../repository/order');
const ServiceError = require('../core/serviceError');

const getAll = async () => {
  const orders = await orderRepository.getAll();
  return {
    orders,
    count: await orderRepository.getCount(),
  };
};

const getById = async (id) => { 
  const order = await orderRepository.getById(id);

  if (!order) {
    throw ServiceError.notFound(`Order with id ${id} not found`, {id});
  }
  return order;
};
const create = async ({userId, AIid,  orderDate, deliveryDate, paymentMethod}) => {
  const newOrder = await orderRepository.create({userId, AIid, orderDate, deliveryDate, paymentMethod});
  return newOrder;
};
const updateById = async (orderId, {userId, AIid, orderDate, deliveryDate, paymentMethod}) => {

  const updatedOrder= await orderRepository.updateById({orderId, userId, AIid, orderDate, deliveryDate, paymentMethod});
  if(!updatedOrder){
    throw ServiceError.notFound(`Order with id ${orderId} not found and not updated`, {orderId});
  }
  return updatedOrder;

};
const deleteById = async (id) => {
  const rowsAffected = await orderRepository.deleteById(id);
  if (!rowsAffected) {
    throw ServiceError.notFound(`Order with id ${id} not found and not deleted`, {id});
  }

};

module.exports = {
  getAll,
  getById,
  create,
  updateById,
  deleteById,
};