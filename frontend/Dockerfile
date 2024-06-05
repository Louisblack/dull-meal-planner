FROM node:21-alpine

WORKDIR /app

COPY build ./build

RUN npm install -g serve

CMD ["serve", "-s", "./build"]
