# Stage 1: Compile and Build angular codebase

FROM node:latest AS build

WORKDIR /app
COPY ./ /app/
RUN npm install
RUN npm run build

# Stage 2: Serve app with nginx server
FROM nginx:latest

COPY --from=build /app/dist/ui /usr/share/nginx/html

EXPOSE 4200
EXPOSE 80
