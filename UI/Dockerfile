# Stage 1: Compile and Build angular codebase

FROM node:latest AS build

WORKDIR /app
COPY ./ /app/
RUN npm install
RUN npm run build

# Stage 2: Serve app with nginx server
FROM nginx:latest

COPY --from=build /app/dist/ui /usr/share/nginx/html
#COPY --from=build /app/nginix.conf  /etc/nginx/conf.d/default.conf
#COPY --from=build /app/a.cer /etc/nginx/certs/default.cer
#COPY --from=build /app/a.pkey /etc/nginx/certs/default.key

EXPOSE 4200
EXPOSE 443
EXPOSE 80

