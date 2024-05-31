module.exports = {
  definition: {
    openapi: '3.0.0',
    info: {
      title: 'AI webshop API',
      version: '0.1.0',
      description: 'simple API for a webshop that sells AI models',
      license: {
        name: 'MIT',
        url: 'https://spdx.org/licenses/MIT.html',
      },
      contact: {
        name: 'AIwebshopAPI',
        email: 'senne.dierick@student.hogent.be',
      },
    },
    servers: [{
      url: 'http://localhost:9000/',
    }],
  },
  apis: ['./src/rest/*.js'],
};