# Examenopdracht ~~Front-end Web Development~~ / Web Services

- Student: Senne Dierick
- Studentennummer: 202185051
- E-mailadres: senne.dierick@student.hogent.be

## Vereisten

Ik verwacht dat volgende software reeds geïnstalleerd is:

- [NodeJS](https://nodejs.org)
- [Yarn](https://yarnpkg.com)
- [MySQL Community Server](https://dev.mysql.com/downloads/mysql/)


## Opstarten

> .env bestand aanmaken en volgende data aan toevoegen;
```NODE_ENV=
NODE_ENV=
DATABASE_USERNAME=
DATABASE_PASSWORD=


AUTH_JWKS_URI=
AUTH_AUDIENCE=
AUTH_ISSUER=
AUTH_USER_INFO=
```



`yarn start`
## Testen

> .env.test bestand aanmaken en volgende data aan toevoegen;
```
NODE_ENV=test
DATABASE_USERNAME=
DATABASE_PASSWORD=

AUTH_TEST_USER_USER_ID=
AUTH_TEST_USER_USERNAME=
AUTH_TEST_USER_PASSWORD=
AUTH_TOKEN_URL=
AUTH_CLIENT_ID=
AUTH_CLIENT_SECRET=
AUTH_JWKS_URI=
AUTH_AUDIENCE=
AUTH_ISSUER=
AUTH_USER_INFO=
```
`yarn test` of `yarn test:coverage`
