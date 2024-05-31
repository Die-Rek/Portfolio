const Koa = require('koa');
const Router = require('@koa/router')
const config = require('config');
const { getLogger } = require('./core/logging');
const bodyparser = require('koa-bodyparser');
const orderService = require('./service/orders');
const app = new Koa();
const logger = getLogger();

//const NODE_ENV = config.get(env)
const LOG_LEVEL = config.get('log.level');
const LOG_DISABLED = config.get('log.disabled');

console.log(`log level ${LOG_LEVEL}, logs enabled: ${LOG_DISABLED !== true}`)

app.use(bodyparser());


const router = new Router()

router.get('/api/orders', async (ctx) =>{
  logger.info(JSON.stringify(ctx.request))
  ctx.body = orderService.getAll()
})

router.post('/api/orders', async (ctx) =>{
  orderService.create({...ctx.request.body,
    plqceId: Number(ctx.request.body.placeId),
    date : new Date(ctx.request.body.date)})
})

app
  .use(router.routes())
  .use(router.allowedMethods())

app.listen(9000)
logger.info('Server running on port 9000');