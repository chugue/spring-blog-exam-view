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
    int currentPage = 1;
    int showPages = 5;
    int prevPage = currentPage - 1;
    int nextPage = currentPage + 1;

    public boolean firstPage(int page, HttpServletRequest request) {
        boolean firstPage;
        currentPage = page;
        firstPage = prevPage == 0;
        request.setAttribute("firstPage", firstPage);
        return firstPage;
    }

    public boolean lastPage(int page) {
        boolean lastPage;
        currentPage = page;
        int totalPosts = boardRepository.findAll().size();
        lastPage = (totalPosts - (showPages * currentPage)) < showPages;
        return lastPage;
    }

    public List<Board> showPages(int page, HttpServletRequest request) {
        currentPage = page;
        List<Board> pageList = boardRepository.findAll();
        int totalPosts = pageList.size();
        int start = (showPages*currentPage) - showPages;
        int end = showPages*currentPage;
        ArrayList<Board> boardList = new ArrayList<>();

        for (int j = start; j < end; j++) {
            if (j > totalPosts){
                break;
            }
            boardList.add(pageList.get(j));
        }
        return boardList;
    }
}