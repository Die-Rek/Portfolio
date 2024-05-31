const userRepository = require('../repository/user');
const ServiceError = require('../core/serviceError');
const {getLogger} = require('../core/logging');

const getAll = async () => {
  const users = await userRepository.getAll();
  return {
    users,
    count: await userRepository.getCount(),
  };
};

const getById = async (id) => { 
  const user = await userRepository.getById(id);
  if (!user) {
    throw new ServiceError.notFound(`User with id ${id} not found`, {id});
  }
  return user;
};

  

const create = async ({name, birthDate, email}) => {
  const logger = getLogger();
  logger.info(`creating new user ${name} ${birthDate} ${email}`);
  const newUser = await userRepository.create({name, birthDate, email});
  return newUser;
};


  
const updateById = async (id, {name, birthdate, email}) => {
  const updatedUser = await userRepository.updateById({id, name, birthdate, email});
  if (!updatedUser) {
    throw new ServiceError.notFound(`User with id ${id} not found and not updated`, {id});
  }
  return updatedUser;
};
const deleteById = async (id) => {
  const deleted = await userRepository.deleteById(id);
  if (!deleted) {
    throw new ServiceError.notFound(`User with id ${id} not found and not deleted`, {id});
  }
};

module.exports = {
  getAll,
  getById,
  create,
  updateById,
  deleteById,
};