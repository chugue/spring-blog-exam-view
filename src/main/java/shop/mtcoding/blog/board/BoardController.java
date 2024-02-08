package shop.mtcoding.blog.board;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BoardController {
    private final BoardRepository boardRepository;
    private final Paging paging;

    @GetMapping("/")
    public String index(HttpServletRequest request) {
        int currentPage = 1;
        System.out.println("index 1");
        List<Board> boardList = paging.showPages(currentPage);
        System.out.println("index 2");
        paging.firstPage(currentPage, request);
        System.out.println("index 3");
        request.setAttribute("boardList", boardList);
        System.out.println("index 4");
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
        return "redirect:/";
    }

    @PostMapping("/board/{id}/delete")
    public String delete(@PathVariable int id) {
        boardRepository.delete(id);
        return "redirect:/";
    }

    @GetMapping("/page/{page}")
    public String paging(@PathVariable("page") int page, HttpServletRequest request) {
        System.out.println(1111);
        boolean lastPage = paging.lastPage(page);
        System.out.println(lastPage);
        if (page == 1) {
            System.out.println("page :" + page);
            return "redirect:/";
        }
        List<Board> boardList = paging.showPages(page);
        request.setAttribute("lastPage", lastPage);
        request.setAttribute("boardList", boardList);
        return "page/" + page;
    }

    @GetMapping("/page/{currentPage}/prevPage")
    public String prevPage(@PathVariable("currentPage") int currentPage) {
        int prevPage = paging.prevPage(currentPage);
        return "page/" + prevPage;
    }

    @GetMapping("/page/{currentPage}/nextPage")
    public String nextPage(@PathVariable("currentPage") int currentPage, HttpServletRequest request) {
        boolean lastpage = paging.lastPage(currentPage);
        if(lastpage){
            request.setAttribute("msg", "잘못된 요청입니다.");
            request.setAttribute("status", 400);
            return "error/40x";
        }
        int nextPage = paging.nextPage(currentPage);
        return "page/" + nextPage;
    }
}
