package com.devictoralmeida.teste.services;

import com.devictoralmeida.teste.dto.request.AnexoRequestDto;
import com.devictoralmeida.teste.entities.Anexo;

import java.util.List;
import java.util.UUID;

public interface BucketService {
  void upload(AnexoRequestDto dto, UUID anexoId);

  void deletarArquivo(Anexo arquivo);

  void deletarArquivos(List<Anexo> anexos);
}
