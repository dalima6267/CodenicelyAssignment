# 📖 Wiki Reader (Wikipedia Assignment App)

A simple Android application built as part of the **Wikipedia Codenicely Assignment**.  
The app fetches and displays **Random Articles**, **Featured Images**, and **Wikipedia Categories** using the official Wikimedia REST APIs.  
It supports **offline reading** via Room Database and provides a **light/dark theme toggle** in the settings.

---

## 🚀 Features
- **3 Tabs (ViewPager + TabLayout)**  
  - Random Wikipedia Articles  
  - Featured Wikimedia Images  
  - Categories list  

- **Pagination / Infinite Scroll**  
  - Uses the `continue` parameter from Wikimedia APIs to fetch the next set of results.

- **Offline Support**  
  - Saves data locally using **Room Database**.  
  - Syncs only missing data from server when network is available.  

- **Theme Switching**  
  - Light, Dark, or System Default theme toggle available via Navigation Drawer → Settings.  

- **Clean Architecture**  
  - MVVM architecture with `ViewModel`, `Repository`, and `Room` database.  
  - Coroutines & suspend functions for async calls.  

---

## 📱 Screensshots

<p align="center">
    <img src="https://github.com/user-attachments/assets/6ed9dd42-5953-4fc0-9352-55a81e743757" alt="first" width="300" height="600">
    <img src="https://github.com/user-attachments/assets/090ebf18-8539-4e78-bc53-5deed55e8d1f" alt="Second" width="300" height="600">
</p>

<p align="center">
    <img src="https://github.com/user-attachments/assets/1c9c8e91-9dce-47ff-bc1c-8e8aadf6f16a" alt="third" width="300" height="600">
     <img src="https://github.com/user-attachments/assets/9167e95c-4dcc-4d76-bd4b-ee8254e99157" alt="fourth" width="300" height="600">
</p>

<p align="center">
    <img src="https://github.com/user-attachments/assets/864ea3c9-d77b-4172-b601-e5631e96638e" alt="fifth" width="300" height="600">
      <img src="https://github.com/user-attachments/assets/823c74af-bb54-463d-8f23-03f98621d396" alt="Sixth" width="300" height="600">
</p>

<p align="center">
    <img src="https://github.com/user-attachments/assets/85c816fa-281c-4e2a-8f1f-f5153ac08c46" alt="Seventh" width="300" height="600">
      <img src="https://github.com/user-attachments/assets/7bb09141-e3ce-461a-864c-e0af6920e1ab" alt="Eight" width="300" height="600">
</p>

 ## Demo

https://github.com/user-attachments/assets/c17795b0-61cf-4866-8d2c-4db8bee5f8f4
