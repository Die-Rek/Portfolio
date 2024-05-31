const Router = require('@koa/router');
const Joi = require('joi');

const AIService = require('../service/AIs');
const { permissions, hasPermission } = require('../core/auth');

const validate = require('./_validation');
/**
 * 
 * @openapi
 * tags:
 *   name: AIs
 *   description: AI management of the products of the company
 */

/**
 * @openapi
 * components:
 *   schemas:
 *     AI:
 *       allOf:
 *         - $ref: '#/components/schemas/Base'
 *         - type: object
 *           required:
 *            - id
 *            - learning_method
 *            - training_time
 *            - activationKey
 *            - unitPrice
 *           properties:
 *             id:
 *               type: 'integer'
 *               format: 'int32'
 *             learning_method:
 *               type: 'string'
 *             training_time:
 *               type: 'integer'
 *             activationKey:
 *               type: 'string'
 *             unitPrice:
 *               type: 'integer'
 *           example:
 *             $ref: '#/components/examples/AI'
 *     AIList:
 *       allOf:
 *        - $ref: '#/components/schemas/ListResponse'
 *        - type: object
 *          required:
 *            - AIs
 *          properties:
 *            AIs:
 *              type: array
 *              items:
 *                $ref: '#/components/schemas/AI'
 *   examples:
 *     AI:
 *      id: 1
 *      learning_method: 'supervised learning'
 *      training_time: 100
 *      activationKey: '2G4sfeK67hF'
 *      unitPrice: 1000
 *   requestBodies:
 *     AI:
 *      description: AI info to save
 *      required: true
 *      content:
 *        application/json:
 *          schema:
 *            type: object
 *            properties:
 *              learning_method:
 *                type: 'string'
 *              training_time:
 *                type: 'integer'
 *              activationKey:
 *                type: 'string'
 *              unitPrice:
 *                type: 'integer'
 *            required:
 *             - learning_method
 *             - training_time
 *             - activationKey
 *             - unitPrice
 */

/**
 * @openapi
 * /api/AIs:
 *   get:
 *     summary: Get all AIs
 *     tags:
 *       - AIs
 *     responses:
 *       200:
 *         description: List of AIs
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/components/schemas/AIList'
 */
const getAIs =  async (ctx) =>{
  ctx.body = await AIService.getAll();
};

const createAI =  async (ctx) =>{
  const newAI = AIService.create({...ctx.request.body});
  ctx.body = newAI;
};

createAI.validationScheme = {
  body: {
    learning_method: Joi.string().max(255),
    training_time: Joi.number(),
    activationKey: Joi.string().max(255),
    unitPrice: Joi.number().positive(),
  },
};
/**
 * 
 * @openapi
 * /api/AIs/{id}:
 *  get:
 *   summary: Get AI by id
 *   tags:
 *     - AIs
 *   parameters:
 *     - $ref: '#/components/parameters/idParam'
 *   responses:
 *     200:
 *       description: AI 
 *       content:
 *         application/json:
 *           schema:
 *             $ref: '#/components/schemas/AI'
 *     404:
 *       description: AI not found
 *       content:
 *         application/json:
 *           schema:
 *             $ref: '#/components/schemas/404NotFound'
 */

const getAIById =  async (ctx) => {
  ctx.body = await AIService.getById(ctx.params.id);
};

getAIById.validationScheme ={
  params: Joi.object({
    id: Joi.number().positive(),
  }),
};

/**
 * 
 * @openapi
 * /api/AIs/{id}:
 *  delete:
 *    summary: Delete AI by id
 *    tags:
 *      - AIs
 *    parameters:
 *      - $ref: '#/components/parameters/idParam'
 *    responses:
 *      204:
 *        description: AI deleted
 *      404:
 *        description: AI not found
 *        content:
 *          application/json:
 *            schema:
 *              $ref: '#/components/schemas/404NotFound'
 */
const deleteAI = async (ctx) => {
  await AIService.deleteById(ctx.params.id);
};

deleteAI.validationScheme={
  params: Joi.object({
    id: Joi.number().positive(),
  }),
};
/**
 * 
 * @openapi
 * /api/AIs/{id}:
 *  put:
 *    summary: Update AI by id
 *    tags:
 *      - AIs
 *    parameters:
 *      - $ref: '#/components/parameters/idParam'
 *    requestBody:
 *      $ref: '#/components/requestBodies/AI'
 *    responses:
 *      200:
 *       description: AI updated
 *       content:
 *         application/json:
 *           schema:
 *             $ref: '#/components/schemas/AI'
 *      400:
 *        description: Invalid AI data
 *        content:
 *          application/json:
 *            schema:
 *              $ref: '#/components/schemas/400BadRequest'
 *      404:
 *        description: AI not found
 *        content:
 *          application/json:
 *            schema:
 *              $ref: '#/components/schemas/404NotFound'
 */
const updateAI = async (ctx) => {
  ctx.body = await AIService.updateById(ctx.params.id, {...ctx.request.body});
};

updateAI.validationScheme={
  params: Joi.object({
    id: Joi.number().positive(),
    learning_method: Joi.string().max(255),
    training_time: Joi.number().positive(),
    activationKey: Joi.string().max(255),
    unitPrice: Joi.number().positive(),
  }),
};

module.exports = (app) => {
  const router = new Router({prefix:'/AIs'});

  router.get('/', hasPermission(permissions.read), getAIs);
  router.get('/:id',hasPermission(permissions.read), validate(getAIById.validationScheme), getAIById);
  router.post('/',hasPermission(permissions.write), validate(createAI.validationScheme), createAI);
  router.put('/:id',hasPermission(permissions.write), validate(updateAI.validationScheme),updateAI);
  router.delete('/:id',hasPermission(permissions.write), validate(deleteAI.validationScheme), deleteAI);
    
  app
    .use(router.routes())
    .use(router.allowedMethods());
};