package repository.dao;

import domain.Reservation;
import exception.DataAccessException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.List;

public class ReservationDaoFileImpl implements ReservationDao {
  private static ReservationDaoFileImpl instance;
  private final File file;

  private ReservationDaoFileImpl() {
    this.file = new File("reservations.csv");
  }

  // visible for testing, so tests can point at a temp file instead of the real one
  ReservationDaoFileImpl(File file) {
    this.file = file;
  }

  // singleton
  public static synchronized ReservationDaoFileImpl getInstance() {
    if (instance == null) {
      instance = new ReservationDaoFileImpl();
    }

    return instance;
  }

  @Override
  public void save(Reservation reservation) throws DataAccessException {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
      String line =
          String.format(
              "%s,%s,%s,%s\n",
              reservation.getId(),
              reservation.getService().getId(),
              reservation.getOwner().getId(),
              reservation.getDate());
      bw.write(line);
    } catch (IOException e) {
      throw new DataAccessException("Error al guardar la reserva", e);
    }
  }
//--------------------------------------------------------------------------------------------------------------
  // Ejercicio 3 (Parte A)
 // REALIZADO PORGIOVANI MICHELOTTI------CLASE:RESERVATIONDAOFILEIMPL---
  
 @Override
public List<Reservation> findByDateRange(Instant from, Instant to) throws DataAccessException {
    try {
        return this.findAll().stream()
                .filter(reservation -> reservation != null && reservation.getDate() != null)
                .filter(reservation -> from == null || !reservation.getDate().isBefore(from))
                .filter(reservation -> to == null || !reservation.getDate().isAfter(to))
                .toList();
    } catch (Exception e) {
        throw new DataAccessException("Error al filtrar reservas por rango de fechas", e);
    }
}
  
}
