package shop.mtcoding.blog.board;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BoardController {
    private final BoardRepository boardRepository;
    private final Paging paging;

    @GetMapping("/")
    public String index(HttpServletRequest request) {
        List<Board> boardList= paging.showPages(1, request);
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
        return "redirect:/1";
    }

    @GetMapping("/page/{page}")
    public String paging(@PathVariable int page) {
        if (page == 1) {
            return "redirect:/";
        }
        int totalPosts = boardRepository.findAll().size();

        return "redirect:/";
    }
}
