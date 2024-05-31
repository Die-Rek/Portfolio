const Joi = require('joi');
let { ORDERS } = require('../data/mock_data');
const validate = require('../rest/_validation.js')
const getAll = () => {
    return { items:ORDERS, count:ORDERS.length };
}

const getById = (id) => {  throw new Error("not implemented yet"); }

getById.ValidationScheme = {
    params: Joi.object({
        id: Joi.number().inteadger().positive()
    })
}
const create = ({amount, date, placeId, user}) => {
	throw new Error("not implemented yet");
}
const updateById = (id, {amount, date, placeId, user}) => {
	throw new Error("not implemented yet");
}
const deleteById = (id) => {
	throw new Error("not implemented yet");
}

module.exports = {
    getAll,
    getById,
    create,
    updateById,
    deleteById
}