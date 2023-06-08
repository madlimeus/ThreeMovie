import { ApolloClient, createHttpLink } from '@apollo/client';
import { cache } from './cache';

const httpLink = createHttpLink({
    // uri: 'https://moviethree.synology.me/api/graphql',
    uri: 'http://localhost:8080/api/graphql',
});

export const client = new ApolloClient({
    link: httpLink,
    cache,
});
