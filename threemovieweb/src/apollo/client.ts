import { ApolloClient, createHttpLink } from '@apollo/client';
import { cache } from './cache';

const httpLink = createHttpLink({
    uri: 'http://moviethree.synologe.me/graphql',
});

export const client = new ApolloClient({
    link: httpLink,
    cache,
});
