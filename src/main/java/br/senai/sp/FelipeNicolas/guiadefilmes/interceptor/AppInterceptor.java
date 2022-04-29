package br.senai.sp.FelipeNicolas.guiadefilmes.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.checkerframework.checker.units.qual.h;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import br.senai.sp.FelipeNicolas.guiadefilmes.annotation.Publico;

@Component
public class AppInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// variavel para obter a URI da request
		String uri = request.getRequestURI();
		// variavel para a sessão
		HttpSession session = request.getSession();
		if (uri.startsWith("/error")) {
			return true;
		}

		// verificar se hanlder é um um HandlerMethod
		// oque indica que ele esta chamando um metodo
		// em algum controller
		if (handler instanceof HandlerMethod) {
			// casting de object para handlermathod
			HandlerMethod metodo = (HandlerMethod) handler;
			if (uri.startsWith("/api")) {
				return true;

			}else {
				
			
			// verifica se o metodo é publico
			if (metodo.getMethodAnnotation(Publico.class) != null) {
				return true;
			}
			// verifica se usuario esta logado
			if (session.getAttribute("usuarioLogado") != null) {
				return true;
			}
			// redireciona para pagina inicial
			response.sendRedirect("/");
			return false;
		}
		}
		return true;
	}
}
