const Router = require('@koa/router');
const Joi = require('joi');

const orderService = require('../service/order');
const { permissions, hasPermission } = require('../core/auth');

const validate = require('./_validation');
/**
 * 
 * @openapi
 * tags:
 *   name: Orders
 *   description: Order management of the products of the company
 */
/**
* @openapi
* components:
*  schemas:
*    Order:
*       - type: object
*         order: 
*          type: object
*          properties: 
*            id: 
*              type: integer
*              format: int32
*              example: 1
*            orderDate: 
*              type: string
*              example: 2021-10-20T22:00:00.000Z
*            deliveryDate: 
*              type: string
*              example: 2021-11-01T23:00:00.000Z
*            paymentMethod: 
*              type: string
*              example: overschrijving
*         user: 
*           type: object
*           properties: 
*             id: 
*               type: integer
*               format: int32
*               example: 1
*             name: 
*               type: string
*               example: Senne Dierick
*         AI: 
*           type: object
*           properties: 
*             id: 
*               type: integer
*               format: int32
*               example: 1
*             learning_method: 
*               type: string
*               example: supervised learning
*   
*   
*    OrderList:
*      allOf:
*        - $ref: '#/components/schemas/ListResponse'
*        - type: array
*          required:
*            - Orders
*            - User
*            - AI
*          properties:
*            Orders:
*              type: object
*              items:
*                $ref: '#/components/schemas/Order'
* 
*  examples:
*   Order:
*    orderId: 1
*    orderDate: '2020-12-01'
*    deliveryDate: '2020-12-02'
*    paymentMethod: 'cash'
*    user:  {userId: 1, name: 'John'}
*    AI: {AIid: 1, learning_method: 'supervised learning'}
*/
/**
 * @openapi
 * /api/orders:
 *  get:
 *    summary: Get all orders
 *    tags:
 *      - Orders
 *    responses:
 *      200:
 *        description: List of orders
 *        content:
 *          application/json:
 *            schema:
 *              $ref: '#/components/schemas/OrderList'
 * 
 */
const getOrders =  async (ctx) =>{
  ctx.body = await orderService.getAll();
};

const createOrder =  async (ctx) =>{
  const newOrder = await orderService.create({...ctx.request.body,
    date : new Date(ctx.request.body.date)});
  ctx.body = newOrder;
  ctx.status = 201;
};

createOrder.validationScheme = {
  body: {
    userId: Joi.number().positive(),
    AIid: Joi.number().positive(),
    orderDate: Joi.date().iso().less('now'),
    deliveryDate: Joi.date(),
    paymentMethod: Joi.string().valid('overschrijving', 'bancontact', 'cash', 'ideal'),
  }}; 

/**
 * @openapi
 * /api/orders:
 *  get:
 *    summary: Get all orders
 *    tags:
 *      - Orders
 *    parameters:
 *      - $ref: '#/components/parameters/idParam'
 *    responses:
 *      200:
 *        description: List of orders
 *        content:
 *          application/json:
 *            schema:
 *              $ref: '#/components/schemas/OrderList'
 * 
 */
const getOrderById =  async (ctx) => {
  ctx.body = await orderService.getById(ctx.params.id);
};
/**
 * @openapi
 * /api/orders/{id}:
 *   get:
 *     summary: Get a single order
 *     tags:
 *      - Orders
 *     parameters:
 *       - $ref: "#/components/parameters/idParam"
 *     responses:
 *       200:
 *         description: The requested order
 *         content:
 *           application/json:
 *             schema:
 *               $ref: "#/components/schemas/Order"
 *       403:
 *         description: Not allowed to read this information
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/components/responses/403Forbidden'
 *       404:
 *         description: No order with the given id could be found
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/components/responses/404NotFound'
 */
getOrderById.validationScheme ={
  params: Joi.object({
    id: Joi.number().positive(),
  }),
};
/**
 * @openapi
 * /api/orders/{id}:
 *   delete:
 *     summary: Delete a order
 *     tags:
 *      - Orders
 *     parameters:
 *       - $ref: "#/components/parameters/idParam"
 *     responses:
 *       204:
 *         description: No response, the delete was successful
 *       403:
 *         description: You can only updateinformation if you're an admin
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/components/responses/403Forbidden'
 *       404:
 *         description: No order with the given id could be found
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/components/responses/404NotFound'
 */
const deleteOrder = async (ctx) => {
  await orderService.deleteById(ctx.params.id);
  ctx.status = 204;
};

deleteOrder.validationScheme={
  params: Joi.object({
    id: Joi.number().positive(),
  }),
};
/**
 * @openapi
 * /api/orders/{id}:
 *   put:
 *     summary: Update an existing order
 *     tags:
 *      - Orders
 *     parameters:
 *       - $ref: "#/components/parameters/idParam"
 *     responses:
 *       200:
 *         description: The updated order
 *         content:
 *           application/json:
 *             schema:
 *               $ref: "#/components/schemas/Order"
 *       403:
 *         description: You can only update information if you are an admin
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/components/responses/403Forbidden'
 *       404:
 *         description: No order with the given id could be found
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/components/responses/404NotFound'
 */
const updateOrder = async (ctx) => {
  ctx.body = await orderService.updateById(ctx.params.id, {...ctx.request.body,
  });
};

updateOrder.validationScheme={
  body:{
    userId: Joi.number().positive(),
    AIid: Joi.number().positive(),
    orderDate: Joi.date().less('now'),
    deliveryDate: Joi.date(),
    paymentMethod: Joi.string().valid('overschrijving', 'bancontact', 'cash', 'ideal', 'payconiq'),
  },
};


module.exports = (app) => {
  const router = new Router({prefix:'/orders'});

  router.get('/', hasPermission(permissions.read), getOrders);
  router.get('/:id', hasPermission(permissions.read), validate(getOrderById.validationScheme), getOrderById);
  router.post('/', hasPermission(permissions.write), validate(createOrder.validationScheme), createOrder);
  router.put('/:id', hasPermission(permissions.write), validate(updateOrder.validationScheme),updateOrder);
  router.delete('/:id', hasPermission(permissions.write), validate(deleteOrder.validationScheme),deleteOrder);
    
  app
    .use(router.routes())
    .use(router.allowedMethods());
};

