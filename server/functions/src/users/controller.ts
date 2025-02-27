import { Request, Response } from 'express';
import * as admin from 'firebase-admin';

export async function create(req: Request, res: Response) {
  try {
    const { displayName, password, email, role } = req.body;

    if (!displayName || !password || !email || !role) {
      return res.status(400).send({ message: 'Missing fields' });
    }

    if (role !== 'patient' && role !== 'therapist') {
      return res.status(401).send({ message: 'Invalid role' });
    }

    const { uid } = await admin.auth().createUser({
      displayName,
      password,
      email,
    });

    await admin.auth().setCustomUserClaims(uid, { role });

    return res.status(201).send({ uid });
  } catch (err) {
    return handleError(res, err);
  }
}

function handleError(res: Response, err: any) {
  return res.status(500).send({ message: `${err.code} - ${err.message}` });
}
