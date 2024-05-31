const { tables, getKnex } = require('../data/index');
const { getLogger } = require('../core/logging');

const formatAI = ({AIid, learning_method, training_time, activationKey, unitPrice, ...rest}) => ({
  ...rest,
  AI: {
    id: AIid,
    learning_method: learning_method,
    training_time: training_time,
    activationKey: activationKey,
    unitPrice: unitPrice,
  },
});

const getAll = async () => {
  const AIs = await getKnex()(tables.AI)
    .select('*')
    .orderBy('AIid', 'asc');
  return AIs.map(formatAI);
};

const getById = async (AIid) => {
  const AI = await getKnex()(tables.AI)
    .first('*')
    .where('AIid', AIid);

  return AI && formatAI(AI);
};

const create = async ({learning_method, training_time, activationKey, unitPrice}) => {
  try{
    const [id] = await getKnex()(tables.AI).insert({
      learning_method,
      training_time,
      activationKey,
      unitPrice,
    });
    return await getById(id);
  } catch (error) {
    const logger = getLogger();
    logger.error('Error in create AI', { error });
    throw error;
  }
};

const updateById = async ({AIid, learning_method, training_time, activationKey, unitPrice}) => {
  try{
    const [id] = await getKnex()(tables.AI).update({
      learning_method,
      training_time,
      activationKey,
      unitPrice,
    })
      .where('AIid', AIid);
    return await getById(id);
  } catch (error) {
    const logger = getLogger();
    logger.error('Error in update order', { error });
    throw new Error('Error in update order');
  }
};

const deleteById = async (AIid) => {
  const rowsAffected = await getKnex() (tables.AI)
    .delete()
    .where('AIid', AIid);
  return rowsAffected > 0;
};

const getCount = async () => {
  const count = await getKnex() (tables.AI)
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