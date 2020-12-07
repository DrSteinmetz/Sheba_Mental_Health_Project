import * as functions from 'firebase-functions';
import * as admin from 'firebase-admin';
// import * as express from 'express';
// import * as cors from 'cors';
// import * as bodyParser from 'body-parser';
// import { routesConfig } from './users/routes-config';

admin.initializeApp();

// const app = express();

// app.use(bodyParser.json());
// app.use(cors({ origin: true }));
// routesConfig(app);

// export const api = functions.https.onRequest(app);

exports.createTherapist = functions.https.onCall(async (data, context) => {
  const { password, email } = data;

  if (!password || !email) {
    // return { error: 'Missing fields' };
    throw new functions.https.HttpsError('invalid-argument', 'Missing fields');
  }
  if (!context.auth?.token.admin) {
    throw new functions.https.HttpsError('permission-denied', 'Unauthorized');
  }

  try {
    const { uid } = await admin.auth().createUser({
      password,
      email,
    });
    await admin.auth().setCustomUserClaims(uid, { therapist: true });
    return { message: `Therapist ${email} added successfuly ` };
  } catch (err) {
    return handleError(err);
  }
});

exports.createPatient = functions.https.onCall(async (data, context) => {
  const { password, email } = data;

  if (!password || !email) {
    throw new functions.https.HttpsError('invalid-argument', 'Missing fields');
  }
  if (!context.auth?.token.therapist) {
    throw new functions.https.HttpsError('permission-denied', 'Unauthorized');
  }

  try {
    const { uid } = await admin.auth().createUser({
      password,
      email,
    });
    await admin.auth().setCustomUserClaims(uid, { patient: true });
    return { message: `Patient ${email} added successfuly ` };
  } catch (err) {
    return handleError(err);
  }
});

function handleError(err: any) {
  //return { error: err.message };
  throw new functions.https.HttpsError('internal', err.message);
}

exports.createAdmin = functions.https.onCall(async (data, context) => {
  const { password, email } = data;
  try {
    const { uid } = await admin.auth().createUser({
      password,
      email,
    });
    await admin.auth().setCustomUserClaims(uid, { admin: true });
    return { message: `Admin ${email} added successfuly ` };
  } catch (err) {
    return handleError(err);
  }
});
