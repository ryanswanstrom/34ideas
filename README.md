This is source code for a web site.

# Installation

1. install play framework

# Run Locally

1. play deps --sync
1. play run

# Deploy to Heroku

1. git clone git@github.com:swGooF/34ideas.git
1. heroku create somename --stack cedar
1. heroku config:add FRAMEWORK_ID=prod USE_PRECOMPILED=true
1. heroku config:add MONGO_URL=value MONGO_PORT=value MONGO_NAME=value MONGO_USER=value MONGO_PW=value
1. git push heroku master
