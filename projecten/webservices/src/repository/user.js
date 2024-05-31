const {getKnex, tables } = require('../data/index');
const {getLogger} = require('../core/logging');

const formatUser = ({userId, name, birthDate, email, ...rest}) => ({
  ...rest,
  user: {
    id: userId,
    name: name,
    birthDate: birthDate,
    email: email,
  },
});

const getAll = async () => {
  const users = await getKnex()(tables.user)
    .select('*')
    .orderBy('userId', 'asc');
  return users.map(formatUser);
};

const getById = async (id) => {
  const user = await getKnex() (tables.user)
    .first('*')
    .where('userId', id);
  return user && formatUser(user);
};

const getByAuth0Id = async (auth0id) => {
  return await getKnex()(tables.user)
    .where('auth0id', auth0id)
    .first();
};

const create = async ({name, birthDate, email}) => {
  try{
    const [id] = await getKnex()(tables.user).insert({
      name,
      birthDate,
      email,
    });
    return await getById(id);
  } catch (error) {
    const logger = getLogger();
    logger.error('Error in create user', { error });
    throw error;
  }
};

const updateById = async ({userId, name, birthDate, email}) => {
  try{
    const [id] = await getKnex()(tables.user).update({
      name,
      birthDate,
      email,
    })
      .where('userId', userId);
    return await getById(id);
  } catch (error) {
    const logger = getLogger();
    logger.error('Error in update user', { error });
    throw error;
  }
};

const deleteById = async (id) => {
  try{
    const rowsAffected = await getKnex()(tables.user).delete().where('userId', id);
    return rowsAffected > 1;
  } catch (error) {
    const logger = getLogger();
    logger.error('Error in delete user', { error });
    throw error;
  }
};

const getCount = async () => {
  const count = await getKnex() (tables.user).count().first();
  return count['count(*)'];
};

  

module.exports = {
  getAll,
  getById,
  create,
  updateById,
  deleteById,
  getCount,
  getByAuth0Id,
};