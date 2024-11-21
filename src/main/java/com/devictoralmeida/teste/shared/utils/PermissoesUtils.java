package com.devictoralmeida.teste.shared.utils;

import com.devictoralmeida.teste.entities.PerfilAcesso;
import com.devictoralmeida.teste.shared.constants.SharedConstants;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PermissoesUtils {
  private PermissoesUtils() {
  }

  public static Collection<? extends GrantedAuthority> getPermissoes(Set<PerfilAcesso> perfisAcessos) {
    return perfisAcessos.stream()
            .flatMap(perfil -> perfil.getPermissoes().stream()
                    .map(permissao -> new SimpleGrantedAuthority(formatarPermissaoModulo(permissao.getNome(), permissao.getModulo().getNome()))))
            .collect(Collectors.toSet());
  }

  public static Collection<? extends GrantedAuthority> getPermissoesFromToken(FirebaseToken token) {
    List<String> permissoes = (List<String>) token.getClaims().get(SharedConstants.PERMISSOES);

    return permissoes.stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toSet());
  }

  public static String formatarPermissaoModulo(String nomePermissao, String nomeModulo) {
    if (ValidarDadosUtils.isNullOrStringVazia(nomePermissao) || ValidarDadosUtils.isNullOrStringVazia(nomeModulo)) {
      return "";
    }

    String nomePermissaoFormatado = nomePermissao.toUpperCase();
    String nomeModuloFormatado = nomeModulo.toUpperCase().replace(" ", "_");

    return String.format("%s_%s", nomePermissaoFormatado, nomeModuloFormatado);
  }
}
