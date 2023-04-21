import { ApolloClient, createHttpLink } from '@apollo/client';
import { cache } from './cache';

const httpLink = createHttpLink({
    uri: 'http://192.168.219.157:8080/graphql',
});

export const client = new ApolloClient({
    link: httpLink,
    cache,
});
