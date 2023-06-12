import {useEffect} from "react";
import {DocumentNode} from "graphql/language";
import {LazyQueryHookOptions, useLazyQuery} from "@apollo/react-hooks";
import {useRecoilState} from "recoil";
import loadingAtom from "../Recoil/Atom/loadingAtom";
import retFilter from "../Util/retFilter";

interface graphQLProp {
	query: DocumentNode;
	filter?: boolean;
	options?: LazyQueryHookOptions;
}

const useGraphQL = ({query, filter = true, options}: graphQLProp) => {
	const [loadingGlobal, setLoading] = useRecoilState(loadingAtom);
	const [refetch, {
		loading,
		error,
		data
	}] = useLazyQuery(query, filter ? {variables: {filter: retFilter()}, ...options} : {...options});
	
	const modifyLoading = (state: boolean) => {
		if (state)
			setLoading(loadingGlobal + 1);
		else
			setLoading(loadingGlobal - 1 < 0 ? 0 : loadingGlobal - 1)
	}
	
	useEffect(() => {
		if (loading)
			modifyLoading(true);
		else
			modifyLoading(false);
	}, [loading]);
	
	useEffect(() => {
		if (error)
			alert(error.message)
	}, [error])
	
	return {data, refetch}
}

export default useGraphQL;
