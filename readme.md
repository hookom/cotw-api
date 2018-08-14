# Deployment

## Create bucket (should already be there)
gsutil mb gs://cotw-api

# GCLOUD AUTH LOCAL (for new machine or when logged out)
gcloud auth application-default login

## Build app
gradle build

## Copy built jar to bucket
gsutil cp build/libs/* gs://cotw-api/cotw.jar

## Restart compute VM instance (manual)

