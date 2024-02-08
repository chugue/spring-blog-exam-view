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
        List<Board> boardList = paging.showPages(1);
        paging.firstPage(1, request);
        request.setAttribute("boardList", boardList);
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
    public String paging(@PathVariable int page, HttpServletRequest request) {
        boolean lastPage = paging.lastPage(page);
        System.out.println(lastPage);
        if (page == 1) {
            return "redirect:/";
        }
        List<Board> boardList = paging.showPages(page);
        System.out.println(1111);
        request.setAttribute("lastPage", lastPage);
        request.setAttribute("boardList", boardList);
        request.setAttribute("page", page);
        return "index";
    }

    @GetMapping("/page/{page}/prevPage")
    public String prevPage(@PathVariable int page) {
        System.out.println(1);
        int prevPage = paging.prevPage(page);
        System.out.println(2);
        System.out.println(prevPage);
        return "redirect:/page/" + prevPage;
    }

    @GetMapping("/page/{nextPage}/nextPage")
    public String nextPage(@PathVariable int nextPage) {
        System.out.println(1);
        nextPage = paging.nextPage(nextPage);
        System.out.println(2);
        return "page/" + nextPage;
    }
}
