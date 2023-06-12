import React, {useEffect, useRef, useState} from "react";
import {Box, Input, InputAdornment} from "@mui/material";
import SearchIcon from "@mui/icons-material/Search";
import {locateMovie} from "../../Util/locateUtil";

const NavSearch = () => {
	const [onSearch, setOnSearch] = useState<boolean>(false);
	const [search, setSearch] = useState<string>('');
	
	const modalRef = useRef<HTMLDivElement>(null);
	useEffect(() => {
		const handleClick = (e: MouseEvent) => {
			if (modalRef.current && !modalRef.current.contains(e.target as Node)) {
				setOnSearch(false);
			}
		};
		window.addEventListener('mousedown', handleClick);
		return () => window.removeEventListener('mousedown', handleClick);
	}, [modalRef]);
	
	const onClickSearch = () => {
		if (onSearch) {
			locateMovie(search)
		}
		setOnSearch(true);
	};
	
	const onChangeSearch = (event: React.ChangeEvent<HTMLTextAreaElement | HTMLInputElement>) => {
		setSearch(event.target.value);
	};
	
	const handleOnKeyPress = (e: React.KeyboardEvent<HTMLInputElement>) => {
		if (e.key === 'Enter') {
			locateMovie(search)
		}
	};
	return (
		<Box className="searchBox">
			<Input
				ref={modalRef}
				disableUnderline
				className={onSearch ? 'searchArea active' : 'searchArea'}
				placeholder="영화 정보를 입력 해주세요."
				endAdornment={
					<InputAdornment position="end" onClick={onClickSearch}>
						<SearchIcon/>
					</InputAdornment>
				}
				value={search}
				onChange={onChangeSearch}
				inputProps={{
					maxLength: 30,
				}}
				onKeyDown={handleOnKeyPress}
			/>
		</Box>
	)
}

export default NavSearch;
