const Joi = require('joi');

const JOI_OPTIONS = {
    abortEarly: true, // stop validatie bij eerste fout
    allowUnkown: false, // geen onbekende velden toelaten
    context: true, //hebben we nodig voor Joi.ref
    convert: true, // waarden casten naar verwachte waarde
    presence: 'required', // alle velden zijn verplicht
}

const cleanupJoiError = (error) => error.details.reduce((resultObj, {
	message,
	path,
	type,
}) => {
	const joinedPath = path.join('.') || 'value';
	if (!resultObj[joinedPath]) {
		resultObj[joinedPath] = [];
	}
	resultObj[joinedPath].push({
		type,
		message,
	});

	return resultObj;
}, {});

const validate= (schema) => {
    if(!schema) { // request zonder iets -> validate(null) als middleware gebruiken
        schema = {
            query :{},
            body: {},
            params: {} // <- nu enkel dit
        }
    }

    return (ctx, next) => {
        const errors = {} //response body indeien validatie gefaald is
        if (!Joi.isSchema(schema.body)) {//indien geen schema er één maken
            // als params undefined: {} gebruiken
            schema.body = Joi.object(schema.body || {});
        }
        return next() // kom niet meer terug in deze middleware
    }

    const {
        error: paramsError,
        value: paramsValue
    } = schema.body.validate(
        ctx.params,
        JOI_OPTIONS,
    )

    if (paramsError) {
        errors.body = cleanopJoiErrors(paramsError);
    } else {
        ctx.body = paramsValue; //mogelijks dingen gecast
    }

    if (Object.keys(errors).length > 0) {
        ctx.throw(400, 'Validation failed, check details for more information'), {
        code: 'VALIDATION_FAILED',
        details: errors
    }
}
}
module.exports=(
    validate
)