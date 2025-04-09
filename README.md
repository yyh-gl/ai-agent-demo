# Spring Boot with Tailwind CSS Integration

This project demonstrates how to integrate Tailwind CSS with a Spring Boot application.

## Prerequisites

- JDK 21 or later
- Gradle 8.x or later
- Node.js 18.x or later (will be automatically downloaded by Gradle)
- npm 9.x or later (comes with Node.js)

## Project Structure

- `src/main/resources/static/css/tailwind.css`: Source CSS file with Tailwind directives
- `src/main/resources/static/css/main.css`: Generated CSS file (don't edit directly)
- `tailwind.config.js`: Tailwind CSS configuration
- `postcss.config.js`: PostCSS configuration
- `package.json`: npm dependencies and scripts

## Development Workflow

### Initial Setup

1. Clone the repository
2. Run `./gradlew npmInstall` to install npm dependencies

### Building the Application

```bash
./gradlew build
```

This will:
1. Install npm dependencies
2. Build CSS with Tailwind
3. Build the Spring Boot application

### Running the Application

#### Standard Run

```bash
./gradlew bootRun
```

This will build the CSS once and then run the application.

#### Development Mode with CSS Watching

```bash
./gradlew bootRunWithCssWatch
```

This will:
1. Run the application
2. Watch for changes in the CSS files and rebuild them automatically

### Building CSS Manually

```bash
./gradlew buildCss
```

### Watching CSS Changes

```bash
./gradlew watchCss
```

## Customizing Tailwind

1. Edit `tailwind.config.js` to customize Tailwind settings
2. Edit `src/main/resources/static/css/tailwind.css` to add custom styles
3. Run `./gradlew buildCss` to rebuild the CSS

## How It Works

1. The source `tailwind.css` file contains Tailwind directives and custom styles
2. PostCSS processes this file with Tailwind and Autoprefixer
3. The processed CSS is output to `main.css`
4. Spring Boot serves the `main.css` file as a static resource

## Troubleshooting

- If you encounter issues with the CSS build, try running `./gradlew npmInstall --refresh-dependencies`
- Check the Node.js and npm versions with `node -v` and `npm -v`
- Ensure that the Tailwind classes you're using are included in the purge paths in `tailwind.config.js`