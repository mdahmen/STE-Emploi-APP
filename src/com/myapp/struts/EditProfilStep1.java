package com.myapp.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import steemploi.persistance.TableProfils;
import steemploi.service.Profil;
import steemploi.service.TypeUtilisateur;
import steemploi.service.Utilisateur;

public class EditProfilStep1 extends Action {

		@Override
		public ActionForward execute(ActionMapping mapping, ActionForm form,
		                             HttpServletRequest request, HttpServletResponse response)
		throws Exception {
				Utilisateur user = null;

				if (request.getSession(false) != null && request.getSession(false).getAttribute("user") != null
				    && request.getSession(false).getAttribute("user") instanceof Utilisateur) {
						user = (Utilisateur) request.getSession(false).getAttribute("user");

					} else {

						request.getRequestDispatcher("/login.jsp").forward(request,
						        response);
						return mapping.findForward("error");
					}

				if (!user.getType().equals(TypeUtilisateur.ETUDIANT)) {
						request.getRequestDispatcher("/login.jsp").forward(request,
						        response);
						return mapping.findForward("error");
					}

				if (form == null)
					return mapping.findForward("error");

				HttpSession s = request.getSession();

				Profil profil = (Profil) form;

				s.removeAttribute("etudforma");

				s.setAttribute("etudforma", profil);
				int etid = user.getEtudiant().getId();
				TableProfils tp = new TableProfils();
				tp.setEtudiantId(etid);
				tp.saveCoordonnees(profil);
				
				return mapping.findForward("success");
			}


	}
