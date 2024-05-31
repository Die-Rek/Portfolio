const AIRepository = require('../repository/AI');
const ServiceError = require('../core/serviceError');

const getAll = async () => {
  const AIs = await AIRepository.getAll();
    
  if (!AIs) {
    throw new ServiceError.notFound('No AI found');
  }
  return {
    AIs,
    count: await AIRepository.getCount(),
  };

};

const getById = async (id) => { 
  const AI = await AIRepository.getById(id);
  if (!AI) {
    throw new ServiceError.notFound(`AI with id ${id} not found`, {id});
  }
    
  return AI;
};
const create = async ({learning_method, training_time, activationKey, unitPrice}) => {
  const newAI = await AIRepository.create({learning_method, training_time, activationKey, unitPrice});
  return newAI;
};
const updateById = async (id, {learning_method, training_time, activationKey, unitPrice}) => {
  const updatedAI = await AIRepository.updateById(id, {learning_method, training_time, activationKey, unitPrice});
  if (!updatedAI) {
    throw new ServiceError.notFound(`AI with id ${id} not found and not updated`, {id});
  }
  return updatedAI;

};
const deleteById = async (id) => {
  const deleted = await AIRepository.deleteById(id);
  if (!deleted) {
    throw new ServiceError.notFound(`AI with id ${id} not found and not deleted`, {id});
  }
};

module.exports = {
  getAll,
  getById,
  create,
  updateById,
  deleteById,
};