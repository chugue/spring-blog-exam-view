package shop.mtcoding.blog.page;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shop.mtcoding.blog.board.Board;
import shop.mtcoding.blog.board.BoardRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Data
@Component
public class Paging {
    private final BoardRepository boardRepository;
    final int  SHOW_PAGES = 5;
    int currentPage ;
    int prevPage ;
    int nextPage ;


    public boolean firstPage(int page) {
        boolean firstPage;
        currentPage = page;
        prevPage = currentPage - 1;
        firstPage = prevPage == 0;
        return firstPage;
    }

    public boolean lastPage(int page) {
        boolean lastPage;
        currentPage = page - 1;
        int totalPosts = boardRepository.findAll().size();
        lastPage = (totalPosts - (SHOW_PAGES*currentPage)) < SHOW_PAGES;
        return lastPage;
    }

    public List<Board> showPages(int page) {
        currentPage = page;
        List<Board> pageList = boardRepository.findAll();
        ArrayList<Board> boardList = new ArrayList<>();
        int totalPosts = pageList.size();
        int start = (SHOW_PAGES*currentPage) - SHOW_PAGES;
        int end = SHOW_PAGES*currentPage;

        for (int j = start; j < end; j++) {
            if (j >= totalPosts){
                break;
            }
            System.out.println(pageList.get(j));
            boardList.add(pageList.get(j));
        }
        return boardList;
    }

    public int totalPages() {
        List<Board> pageList = boardRepository.findAll();
        int totalPosts = pageList.size();
        int calculation = totalPosts%SHOW_PAGES;
        int totalPages = (calculation < SHOW_PAGES)? calculation + 1 : totalPosts/SHOW_PAGES ;

        return totalPages;
    }


}
