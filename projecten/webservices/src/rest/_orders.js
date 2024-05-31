const Router = require('@koa/router');
const Joi = require('joi');

const orderService = require('../service/orders.js');

const validate = require('./_validation.js');

const getOrderById = async (ctx) => {
    ctx.body = await orderService.getById(ctx.params.id)
}
getOrderById.ValidationScheme = {}



const createOrder = async (ctx) => {}

createOrder.ValidationScheme = {
    body: {
        // checks invullen voor elke parameter
    }
}