package notes.httpRequests;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpDelete;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.http.entity.StringEntity;
//import java.io.IOException;
import java.io.*;
import notes.models.Category;
import notes.models.UserCategoryLink;
import notes.models.Note;
import notes.models.User;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.entity.ContentType;
import notes.repository.CategoryRepository;
import notes.repository.NoteRepository;

public class HttpRequest {
    NoteRepository noteRepo = new NoteRepository();
    String defUrl = "http://192.168.1.73:8080/users/";
    public HttpRequest(){
    }


    public User authorization(String nickname, String password) {
        HttpClient httpClient = HttpClients.createDefault();
        String url = defUrl;
        // HttpGetWithEntity httpGet = new HttpGetWithEntity(url);
        HttpPatch httpPatch = new HttpPatch(url);
        // Подготавливаем JSON с логином и паролем
        String json = "{\"nickname\": \"" + nickname + "\", \"password\": \"" + password + "\"}";
        StringEntity httpentity = new StringEntity(json, ContentType.APPLICATION_JSON);
        httpPatch.setEntity(httpentity);

        // Отправляем запрос и получаем ответ
        try {
            HttpResponse response = httpClient.execute(httpPatch);

            // Получаем код ответа
            int statusCode = response.getStatusLine().getStatusCode();

            // Проверяем успешность запроса
            if (statusCode == 200) {
                // Получаем тело ответа
                HttpEntity entity = response.getEntity();
                String responseBody = EntityUtils.toString(entity);

                // Парсим JSON-ответ с помощью Gson
                Gson gson = new Gson();

                User userSer = gson.fromJson(responseBody, User.class);

                //извлекаем user
                int userId = userSer.getId();

                User user = new User(nickname, password, userId);

                //извлекаем список UserCategory связей
                List<UserCategoryLink> userCategoryLinks = userSer.getUserCategoryLinks();

                List<Note> allNotes = new ArrayList<>();
                //извлекаем список категорий
                for (UserCategoryLink link : userCategoryLinks) {
                    Category category = link.getCategory();
                    int categoryId = category.getCategory_id(); // Получаем ID категории
                    List<Note> notes = link.getNotes();
                    // Устанавливаем categoryId для каждой заметки
                    for (Note note : notes) {
                        note.setCategory(categoryId);
                    }
                    allNotes.addAll(notes);
                }

                //Удаляем старые заметки
                List<Note> oldNotes= noteRepo.getAllNotes();
                for (Note note : oldNotes) {
                    noteRepo.deleteNote(note.getId());
                }

                if (!allNotes.isEmpty()) {
                //Добавляем новые если они есть
                for (Note note : allNotes) {
                    noteRepo.addNote(note);
                }

                } else {
                    System.out.println("Server empty");
                }
                return user;

            } else if (statusCode == 404) {
                System.out.println("User not found");
                return null;
            } else {
                System.out.println("Unexpected status code: " + statusCode);
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateData(long userId) {
        // Создаем HttpClient
        HttpClient httpClient = HttpClients.createDefault();

        // Формируем URL для запроса
        String url = defUrl + userId;

        // Создаем GET-запрос
        HttpGet request = new HttpGet(url);

        try {
            // Отправляем запрос на сервер
            HttpResponse response = httpClient.execute(request);

            // Получаем код ответа
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == 200) {
                // Получаем тело ответа
                HttpEntity entity = response.getEntity();
                String responseBody = EntityUtils.toString(entity);

                // Парсим JSON-ответ с помощью Gson
                Gson gson = new Gson();

                User userSer = gson.fromJson(responseBody, User.class);

                //извлекаем список UserCategory связей
                List<UserCategoryLink> userCategoryLinks = userSer.getUserCategoryLinks();

                List<Note> allNotes = new ArrayList<>();

                //извлекаем список категорий
                for (UserCategoryLink link : userCategoryLinks) {
                    Category category = link.getCategory();
                    int categoryId = category.getCategory_id(); // Получаем ID категории
                    List<Note> notes = link.getNotes();
                    // Устанавливаем categoryId для каждой заметки
                    for (Note note : notes) {
                        note.setCategory(categoryId);
                    }
                    allNotes.addAll(notes);
                }

                //Удаляем старые заметки
                List<Note> oldNotes= noteRepo.getAllNotes();
                for (Note note : oldNotes) {
                    noteRepo.deleteNote(note.getId());
                }

                if (!allNotes.isEmpty()) {
                    //Добавляем новые если они есть
                    for (Note note : allNotes) {
                        noteRepo.addNote(note);
                    }

                } else {
                    System.out.println("Server empty");
                }

                return true;
            } else if (statusCode == 404) {
                // Если пользователь не найден (код 404), возвращаем false
                return false;
            } else {
                // В случае других кодов ответа, бросаем исключение
                throw new IOException("Unexpected status code: " + statusCode);
            }
        } catch (IOException e) {
            // Обработка ошибок ввода-вывода
            e.printStackTrace();
            return false; // Возвращаем false в случае ошибки
        }
    }
    public boolean registration(String nickname, String password) {
        // Создаем HttpClient
        HttpClient httpClient = HttpClients.createDefault();

        // Формируем URL для запроса
        String url = defUrl;

        // Создаем POST-запрос
        HttpPost request = new HttpPost(url);

        try {

            // Создаем JSON с никнеймом и паролем
            String json = "{\"nickname\": \"" + nickname + "\", \"password\": \"" + password + "\"}";
            StringEntity httpEntity = new StringEntity(json, ContentType.APPLICATION_JSON);

            request.setEntity(httpEntity);


            // Отправляем запрос на сервер
            HttpResponse response = httpClient.execute(request);

            // Получаем код ответа
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == 201) {
                return true;
            }
            else
                return false;
        } catch (IOException e) {
            // Обработка ошибок ввода-вывода
            e.printStackTrace();
            return false; // Возвращаем false в случае ошибки
        }
    }

    public long createNote(User user, Note note) {
        // Создаем HttpClient
        HttpClient httpClient = HttpClients.createDefault();
        int categoryId = note.getCategory();
        int userId = user.getId();
        // Формируем URL для запроса
        String url = defUrl + userId + "/category/" + categoryId + "/notes";
        String content = note.getContent();
        String header = note.getTitle();

         long date = note.getDate();                //long or string????
        // Создаем POST-запрос
        HttpPost request = new HttpPost(url);

        try {
            // Создаем JSON объект с данными заметки
            JsonObject json = new JsonObject();
            json.addProperty("note", content);
            json.addProperty("date", date);
            json.addProperty("header", header);

            // Устанавливаем тело запроса
            StringEntity entity = new StringEntity(json.toString());
            request.setEntity(entity);
            request.setHeader("Content-type", "application/json");
            // Отправляем запрос на сервер
            HttpResponse response = httpClient.execute(request);

            // Получаем код ответа
            int statusCode = response.getStatusLine().getStatusCode();

            // Если запрос выполнен успешно (код 201), извлекаем id созданной заметки из ответа
            if (statusCode == 201) {
             String responseBody = EntityUtils.toString(response.getEntity());
                long noteId  = Long.parseLong(responseBody); //jsonResponse.getAsJsonPrimitive("id").getAsLong();
                // Выводим значение ID заметки
                return noteId;
            }
            else if (statusCode == 404){
                return -10;
            }
            else {
                // В случае ошибки возвращаем -1
                return -1;
            }
        } catch (IOException e) {
            // Обработка ошибок ввода-вывода
            e.printStackTrace();
            return -12; // Возвращаем -1 в случае ошибки
        }
    }

    public boolean updateUser(User user) {
        // Создаем HttpClient
        HttpClient httpClient = HttpClients.createDefault();

        int userId = user.getId();
        String nickname = user.getUsername();
        String password = user.getPassword();
        // Формируем URL для запроса
        String url = defUrl + userId;

        // Создаем PUT-запрос
        HttpPut request = new HttpPut(url);

        try {
            // Создаем JSON объект с новыми данными пользователя
            JsonObject json = new JsonObject();
            json.addProperty("nickname", nickname);
            json.addProperty("password", password);

            // Устанавливаем тело запроса
            StringEntity entity = new StringEntity(json.toString());
            request.setEntity(entity);
            request.setHeader("Content-type", "application/json");

            // Отправляем запрос на сервер
            HttpResponse response = httpClient.execute(request);

            // Получаем код ответа
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                return true;
            }
            else {
                return false;
            }
        } catch (IOException e) {
            // Обработка ошибок ввода-вывода
            e.printStackTrace();
            return false; // Возвращаем -1 в случае ошибки
        }
    }

    public boolean updateNote(User user, Note note) {
        // Создаем HttpClient
        HttpClient httpClient = HttpClients.createDefault();
        int userId = user.getId();
        long noteId = note.getId();
        String content = note.getContent();
        Long date = note.getDate();
        int categoryId = note.getCategory();
        String header = note.getTitle();
        // Формируем URL для запроса
        String url = defUrl + userId + "/category/" + categoryId + "/notes/" + noteId;

        // Создаем PUT-запрос
        HttpPut request = new HttpPut(url);

        try {
            // Создаем JSON объект с новыми данными заметки
            JsonObject json = new JsonObject();
            json.addProperty("note", content);
            json.addProperty("date", date);
            json.addProperty("header", header);

            // Устанавливаем тело запроса
            StringEntity entity = new StringEntity(json.toString());
            request.setEntity(entity);
            request.setHeader("Content-type", "application/json");

            // Отправляем запрос на сервер
            HttpResponse response = httpClient.execute(request);

            // Получаем код ответа
            int statusCode = response.getStatusLine().getStatusCode();

            // Возвращаем код ответа
            if (statusCode == 200) {
                return true;
            }
            else {
                return false;
            }
        } catch (IOException e) {
            // Обработка ошибок ввода-вывода
            e.printStackTrace();
            return false; // Возвращаем -1 в случае ошибки
        }
    }

    public boolean deleteNote(User user, long categoryId, long noteId) {
        // Создаем HttpClient
        HttpClient httpClient = HttpClients.createDefault();
        // Формируем URL для запроса

        int userId = user.getId();
        String url = defUrl + userId + "/category/" + categoryId + "/notes/" + noteId;

        // Создаем DELETE-запрос
        HttpDelete request = new HttpDelete(url);

        try {
            // Отправляем запрос на сервер
            HttpResponse response = httpClient.execute(request);

            // Получаем код ответа
            int statusCode = response.getStatusLine().getStatusCode();
            // Возвращаем код ответа
            if (statusCode == 200) {
                return true;
            }
            else {
                return false;
            }
        } catch (IOException e) {
            // Обработка ошибок ввода-вывода
            e.printStackTrace();
            return false; // Возвращаем -1 в случае ошибки
        }
    }

}
