const Koa = require('koa');
const config = require('config');
const bodyparser = require('koa-bodyparser');
const { serializeError } = require('serialize-error');
const swaggerJSdoc = require('swagger-jsdoc');
const { koaSwagger } = require('koa2-swagger-ui');

const swaggerOptions = require('../swagger.config');

const { getLogger, initializeLogger } = require('./core/logging');
const installRest = require('./rest/index');
const { initializeData } = require('./data');
const { shutDownData } = require('./data');
const ServiceError = require('./core/serviceError');
const { checkJwtToken } = require('./core/auth');



const NODE_ENV = process.env.NODE_ENV;
const LOG_LEVEL = config.get('log.level');
const LOG_DISABLED = config.get('log.disabled');

console.log(`log level ${LOG_LEVEL}, logs enabled: ${LOG_DISABLED !== true}`);


module.exports = async function createServer(){
  
  initializeLogger({
    level: LOG_LEVEL,
    disabled: LOG_DISABLED,
    defaultMeta:{
      NODE_ENV,
    },
  });
  const logger = getLogger();
  await initializeData();

  const app = new Koa();



  app.use(checkJwtToken());

  app.use(async (ctx, next) => {
    logger.debug(`token: ${JSON.stringify(ctx.state.user)}`);
    logger.debug(`current user: ${JSON.stringify(ctx.state.user)}`);
    logger.debug(`error in token: ${ctx.state.jwtOriginalError}`);
    await next();
  });

  app.use(bodyparser());

  const spec = swaggerJSdoc(swaggerOptions);
  app.use(koaSwagger({
    routePrefix: '/swagger',
    specPrefix: '/swagger/spec',
    exposeSpec: true,
    swaggerOptions: {
      spec,
    },
  }));

  app.use(async (ctx, next) => {
    const logger = getLogger();
    logger.info(`Request received: ${ctx.method} ${ctx.url}`);

    try{
      await next();
      logger.info(`Request processed: ${ctx.method} ${ctx.status} ${ctx.url}`);
    } catch(error){
      logger.error(`Request failed: ${ctx.method} ${ctx.status} ${ctx.url}`, {error});
    }
  });

  app.use(async (ctx, next) => {
    try{
      await next();

      if (ctx.status === 404){
        ctx.body = {
          code: 'NOT_FOUND',
          message: 'Resource not found',
        };
        ctx.status = 404;
      }
    } catch(error){
      logger.error('Error occured while handling request ', {error: serializeError(error)});

      let statusCode = error.status || 500;
      let errorBody = {
        code: error.code || 'INTERNAL_SERVER_ERROR',
        message: error.message,
        details: error.details || {},
        stack: NODE_ENV !== 'production' ? error.stack : undefined,
      };

      if (error instanceof ServiceError){
        switch (error){
          case error.isNotFound:
            statusCode = 404;
            break;
          case error.isValidationError:
            statusCode = 400;
            break;
          case error.isForbidden:
            statusCode = 403;
            break;
          case error.isUnauthorized:
            statusCode = 401;
            break;


        }
        if (ctx.state.jwtOriginalError) {
          statusCode = 401;
          errorBody.code = 'UNAUTHORIZED';
          errorBody.message = ctx.state.jwtOriginalError.message;
          errorBody.details.jwtOriginalError = serializeError(ctx.state.jwtOriginalError);
        }

        ctx.status = statusCode;
        ctx.body = errorBody;
      }

    }
  });


  installRest(app);

  return {
    getApp(){
      return app;
    },
  

    start(){
      return new Promise((resolve) => {
        const port = config.get('port');
        app.listen(port);
        logger.info(`Server running on port ${port}`);
        resolve();
      });
    },

    async stop(){
      app.removeAllListeners();
      await shutDownData();
      getLogger().info('Server stopped');
    },
  

  };
};

