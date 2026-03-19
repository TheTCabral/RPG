package com.aetherhold.domain.exception;

/**
 * Exceção base do domínio.
 */
abstract class DomainException extends RuntimeException {
    public DomainException(String message) {
        super(message);
    }

    public DomainException(String message, Throwable cause) {
        super(message, cause);
    }
}

/**
 * Ação inválida no contexto atual.
 */
class InvalidActionException extends DomainException {
    public InvalidActionException(String message) {
        super(message);
    }
}

/**
 * Mana insuficiente para usar habilidade.
 */
class InsufficientManaException extends DomainException {
    public InsufficientManaException(String message) {
        super(message);
    }
}

/**
 * Personagem morreu — não pode agir.
 */
class CharacterDeadException extends DomainException {
    public CharacterDeadException(String message) {
        super(message);
    }
}

/**
 * Item não encontrado no inventário.
 */
class ItemNotFoundException extends DomainException {
    public ItemNotFoundException(String message) {
        super(message);
    }
}

