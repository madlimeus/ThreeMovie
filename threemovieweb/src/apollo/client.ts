import { ApolloClient, createHttpLink } from '@apollo/client';
import { cache } from './cache';

const httpLink = createHttpLink({
    uri: 'http://moviethree.synology.me:8080/graphql',
});

export const client = new ApolloClient({
    link: httpLink,
    cache,
});
