package shop.mtcoding.blog.board;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import shop.mtcoding.blog.page.Page;
import shop.mtcoding.blog.page.Paging;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class BoardController {
    private final BoardRepository boardRepository;
    private final Paging paging;

    @GetMapping("/{page}")
    public String paging(@PathVariable int page, HttpServletRequest request) {
        int currentPage = page;

        boolean lastPage = paging.lastPage(currentPage);
        boolean firstPage = paging.firstPage(currentPage);
        int totalPages = paging.totalPages();

        List<Page> pages = new ArrayList<>();

        for (int i = 1; i <= totalPages; i++) {
            Page page1 = new Page();
            page1.setNumber(i);
            page1.setActive(currentPage == i);
            pages.add(page1);
        }
        List<Board> boardList = paging.showPages(currentPage);

        request.setAttribute("pages", pages);
        request.setAttribute("firstPage", firstPage);
        request.setAttribute("lastPage", lastPage);
        request.setAttribute("boardList", boardList);
        request.setAttribute("prevPage", Math.max(1, currentPage - 1));
        request.setAttribute("nextPage", Math.min(totalPages, currentPage + 1));

        return "index";
    }

    @GetMapping( "/")
    public String index( HttpServletRequest request) {
        int currentPage = 1;

        boolean lastPage = paging.lastPage(currentPage);
        boolean firstPage = paging.firstPage(currentPage);
        int totalPages = paging.totalPages();

        List<Page> pages = new ArrayList<>();

        for (int i = 1; i <= totalPages; i++) {
            Page page1 = new Page();
            page1.setNumber(i);
            page1.setActive(currentPage == i);
            pages.add(page1);
        }
        List<Board> boardList = paging.showPages(currentPage);

        request.setAttribute("pages", pages);
        request.setAttribute("firstPage", firstPage);
        request.setAttribute("lastPage", lastPage);
        request.setAttribute("boardList", boardList);
        request.setAttribute("prevPage", Math.max(1, currentPage - 1));
        request.setAttribute("nextPage", Math.min(totalPages, currentPage + 1));

        return "index";
    }

    @GetMapping("/board/saveForm")
    public String saveForm() {
        return "board/saveForm";
    }

    @GetMapping("/board/{id}/updateForm")
    public String updateForm(@PathVariable int id) {
        return "board/updateForm";
    }

    @PostMapping("/board/save")
    public String save(BoardRequest.SaveDTO requestDTO, HttpServletRequest request) {
        if (requestDTO.getTitle().length() > 20) {
            request.setAttribute("msg", "제목은 20자를 초과할 수 없습니다.");
            request.setAttribute("status", 400);
            return "error/40x";
        }
        if (requestDTO.getContent().length() > 20) {
            request.setAttribute("msg", "내용은 20자를 초과할 수 없습니다.");
            request.setAttribute("status", 400);
            return "error/40x";
        }
        boardRepository.save(requestDTO);
        return "redirect:/";
    }

    @PostMapping("/board/{id}/update")
    public String update(@PathVariable int id, BoardRequest.UpdateDTO requestDTO) {
        boardRepository.update(id, requestDTO);
        return "redirect:/" + id;
    }

    @PostMapping("/board/{id}/delete")
    public String delete(@PathVariable int id) {
        boardRepository.delete(id);
        return "redirect:/";
    }
}
