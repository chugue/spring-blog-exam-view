package shop.mtcoding.blog.board;


import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Data
@Component
public class Paging {
    private final BoardRepository boardRepository;
    final int  SHOW_PAGES = 5;
    int currentPage = 1;
    int prevPage ;
    int nextPage ;


    public boolean firstPage(int page, HttpServletRequest request) {
        boolean firstPage;
        currentPage = page;
        prevPage = currentPage - 1;
        firstPage = prevPage == 0;
        request.setAttribute("firstPage", firstPage);
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
            boardList.add(pageList.get(j));
        }
        return boardList;
    }

    public int nextPage(int currentPage){
        nextPage = currentPage + 1;
        return nextPage;
    }

    public int prevPage(int currentPage){
        prevPage = currentPage -1;
        return prevPage;
    }
}
