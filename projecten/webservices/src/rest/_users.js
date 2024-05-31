const Router = require('@koa/router');
const Joi = require('joi');

const userService = require('../service/users');
const { permissions, hasPermission } = require('../core/auth');
const { getLogger } = require('../core/logging');

const validate = require('./_validation');

/**
 * @openapi
 * tags:
 *  name: Users
 *  description: User management of the clients of the company
 */

/**
 * @openapi
 * components:
 *   schemas:
 *     User:
 *       allOf:
 *         - type: object
 *           required:
 *             - name
 *             - birthdate
 *           properties:
 *             name:
 *               type: "string"
 *             birthdate:
 *               type: "date"
 *             email:
 *               type: "string"
 *           example:
 *             $ref: '#/components/examples/User'
 *         - $ref: '#/components/schemas/Base'
 *     UserList:
 *       allOf:
 *         - $ref: '#/components/schemas/ListResponse'
 *         - type: object
 *           required:
 *             - users
 *           properties:
 *             users:
 *               type: array
 *               items:
 *                 $ref: '#/components/schemas/User'
 *   examples:
 *     User:
 *       id: 1
 *       name: Senne Dierick
 *       birthDate: 2002-01-21
 *   requestBodies:
 *     User:
 *       description: User info to save
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             properties:
 *               name:
 *                 type: string
 *               birthdate:
 *                 type: date
 *               email:
 *                 type: string
 *             required:
 *               - name
 */

/**
 * @openapi
 * /api/users/:
 *   get:
 *     summary: Get all users
 *     tags:
 *      - Users
 *     responses:
 *       200:
 *         description: List of users
 *         content:
 *           application/json:
 *             schema:
 *               $ref: "#/components/schemas/UserList"
 */
const getUsers = async (ctx) =>{

  ctx.body = await userService.getAll();
};

const createUser =  async (ctx) =>{
  const logger = getLogger();
  logger.info(`creating new user ${JSON.stringify(ctx.request.body)}`);
  const newOrder = await userService.create({...ctx.request.body,
    birthDate: new Date(ctx.request.body.birthdate)});
  logger.info('new user created', newOrder);
  ctx.body = newOrder;
  ctx.status = 201;
};

createUser.validationScheme = {
  body: {
    name: Joi.string().min(3).max(30),
    birthdate: Joi.date().less('now'),
    email: Joi.string().email(),
  }};

/**
 * 
 * @openapi
 * /api/users/{userId}:
 *  get:
 *   summary: Get a user by id
 *   tags:
 *    - Users
 *   parameters:
 *    - $ref: '#/components/parameters/idParam'
 *   responses:
 *    200:
 *       description: User
 *       content:
 *         application/json:
 *           schema:
 *             $ref: '#/components/schemas/User'
 *    404:
 *     description: User not found
 *     content:
 *      application/json:
 *        schema:
 *          $ref: '#/components/responses/404NotFound'
 */
const getUserById =  async (ctx) => {
  ctx.body = await userService.getById(ctx.params.id);
};

getUserById.validationScheme ={
  params: Joi.object({
    id: Joi.number().positive(),
  }),
};
/**
 * 
 * @openapi
 * /api/users/{userId}:
 *   delete:
 *     summary: Delete a user
 *     tags:
 *       - Users
 *     parameters:
 *       - $ref: '#/components/parameters/idParam'
 *     responses:
 *       204:
 *         description: User deleted
 *       404:
 *         description: User not found
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/components/responses/404NotFound'
 */
const deleteUser = async (ctx) => {
  await userService.deleteById(ctx.params.id);
};

deleteUser.validationScheme={
  params: Joi.object({
    id: Joi.number().positive(),
  }),
};
/**
 * 
 * @openapi
 * /api/users/{userId}:
 *   put:
 *     summary: Update a user
 *     tags:
 *      - Users
 *     parameters:
 *       - $ref: '#/components/parameters/idParam'
 *     requestBody:
 *       $ref: '#/components/requestBodies/User'
 *     responses:
 *       200:
 *         description: User updated
 *         content:
 *            application/json:
 *              schema:
 *                $ref: '#/components/schemas/User'
 *       400:
 *         description: Invalid user data
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/components/responses/400BadRequest'
 *       404:
 *         description: User not found
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/components/responses/404NotFound'
 * 
 */
const updateUser = async (ctx) => {
  ctx.body = await userService.updateById(ctx.params.id, {...ctx.request.body});
};

updateUser.validationScheme={
  params: Joi.object({
    id: Joi.number().positive(),
    name: Joi.string().min(3).max(30),
    birthdate: Joi.date().iso().less('now'),
    email: Joi.string().email(),
  }),
};

module.exports = (app) => {
  const router = new Router({prefix:'/users'});

  router.get('/', hasPermission(permissions.read), getUsers);
  router.get('/:id', hasPermission(permissions.read), validate(getUserById.validationScheme), getUserById);
  router.post('/', hasPermission(permissions.write), validate(createUser.validationScheme), createUser);
  router.put('/:id', hasPermission(permissions.write), validate(updateUser.validationScheme), updateUser);
  router.delete('/:id', hasPermission(permissions.write), validate(deleteUser.validationScheme), deleteUser);
    
  app
    .use(router.routes()) 
    .use(router.allowedMethods());
};